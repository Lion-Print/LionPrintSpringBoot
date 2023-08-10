package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.dto.CurrencyDto;
import uz.bprodevelopment.logisticsapp.entity.Currency;
import uz.bprodevelopment.logisticsapp.repo.CurrencyRepo;
import uz.bprodevelopment.logisticsapp.spec.CurrencySpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepo repo;

    @Override
    public CurrencyDto getOne(Long id) {
        Currency item = repo.getReferenceById(id);
        return item.toDto();
    }

    @Override
    public List<CurrencyDto> getListAll(
            Long companyId,
            String sort
    ) {
        CurrencySpec spec1 = new CurrencySpec(new SearchCriteria("id", ">", 0));
        Specification<Currency> spec = Specification.where(spec1);

        List<Currency> items = repo.findAll(spec, Sort.by(sort).descending());
        List<CurrencyDto> itemDtos = new ArrayList<>();
        items.forEach(category -> itemDtos.add(category.toDto()));

        return itemDtos;
    }

    @Override
    public CustomPage<CurrencyDto> getList(
            Integer page,
            Integer size,
            Long companyId,
            String sort
    ) {

        CurrencySpec spec1 = new CurrencySpec(new SearchCriteria("id", ">", 0));
        Specification<Currency> spec = Specification.where(spec1);

        if (companyId != null) spec = spec.and(new CurrencySpec(new SearchCriteria("companyId", ":", companyId)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Currency> responsePage = repo.findAll(spec, pageable);
        List<CurrencyDto> dtos = new ArrayList<>();
        responsePage.getContent().forEach(item -> dtos.add(item.toDto()));

        return new CustomPage<>(
                dtos,
                responsePage.isFirst(),
                responsePage.isLast(),
                responsePage.getNumber(),
                responsePage.getTotalPages(),
                responsePage.getTotalElements()
        );
    }

    @Override
    public void save(CurrencyDto item) {
        if(item.getCurrencyValueInUzs() == null) {
            throw new RuntimeException("Valuta qiymatini kiriting");
        }
        if(item.getCompanyId() == null) {
            throw new RuntimeException("Kompaniya ID si kiritilmagan");
        }
        if(item.getCurrencyTypeId() == null) {
            throw new RuntimeException("Valyuta turi ID si kiritilmagan");
        }
        if (item.getId() != null) {
            throw new RuntimeException("ID yuborish mumkin emas");
        }

        if (!repo.findAllByCurrencyTypeIdAndCompanyId(item.getCurrencyTypeId(), item.getCompanyId()).isEmpty()) {
            throw new RuntimeException("Bunday valyuta qo'shilgan");
        }

        Currency category = item.toEntity();
        repo.save(category);
    }

    @Override
    @Transactional
    public void update(CurrencyDto item) {
        if(item.getCurrencyValueInUzs() == null) {
            throw new RuntimeException("Valuta qiymatini kiriting");
        }
        Currency currency = repo.getReferenceById(item.getId());
        currency.setCurrencyValueInUzs(item.getCurrencyValueInUzs());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
