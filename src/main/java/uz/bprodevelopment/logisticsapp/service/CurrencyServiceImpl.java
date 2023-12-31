package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.CurrencyDto;
import uz.bprodevelopment.logisticsapp.entity.Currency;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;
import uz.bprodevelopment.logisticsapp.repo.CurrencyRepo;
import uz.bprodevelopment.logisticsapp.repo.CurrencyTypeRepo;
import uz.bprodevelopment.logisticsapp.spec.CurrencySpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepo repo;
    private final CurrencyTypeRepo currencyTypeRepo;
    private final MessageSource messageSource;

    @Override
    public CurrencyDto getOne(Long id) {
        Currency item = repo.getReferenceById(id);
        return item.toDto();
    }

    @Override
    public List<CurrencyDto> getListAll(
            Long supplierId,
            String sort
    ) {
        CurrencySpec spec1 = new CurrencySpec(new SearchCriteria("id", ">", 0));
        Specification<Currency> spec = Specification.where(spec1);

        if (supplierId != null) spec = spec.and(new CurrencySpec(new SearchCriteria("supplierId", ":", supplierId)));

        List<Currency> items = repo.findAll(spec, Sort.by(sort).descending());
        List<CurrencyDto> itemDtos = new ArrayList<>();
        items.forEach(category -> itemDtos.add(category.toDto()));

        return itemDtos;
    }

    @Override
    public CustomPage<CurrencyDto> getList(
            Integer page,
            Integer size,
            Long supplierId,
            String sort
    ) {

        CurrencySpec spec1 = new CurrencySpec(new SearchCriteria("id", ">", 0));
        Specification<Currency> spec = Specification.where(spec1);

        if (supplierId != null) spec = spec.and(new CurrencySpec(new SearchCriteria("supplierId", ":", supplierId)));

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
        if(item.getCurrencyValueInUzs() == null) throw new RuntimeException(messageSource.getMessage("enter_currency_value", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getSupplierId() == null) throw new RuntimeException(messageSource.getMessage("enter_company", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getCurrencyTypeId() == null) throw new RuntimeException(messageSource.getMessage("enter_currency_type", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (item.getId() != null) throw new RuntimeException(messageSource.getMessage("do_not_send_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (!repo.findAllByCurrencyTypeIdAndSupplierId(item.getCurrencyTypeId(), item.getSupplierId()).isEmpty())
            throw new RuntimeException(messageSource.getMessage("name_exist", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        CurrencyType currencyType = currencyTypeRepo.getReferenceById(item.getCurrencyTypeId());

        if (currencyType.getSymbol().equals("UZS")) {
            item.setCurrencyValueInUzs(1.0);
        }

        Currency category = item.toEntity();

        repo.save(category);
    }

    @Override
    @Transactional
    public void update(CurrencyDto item) {
        if(item.getCurrencyValueInUzs() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_currency_value", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        CurrencyType currencyType = currencyTypeRepo.getReferenceById(item.getCurrencyTypeId());
        if (currencyType.getSymbol().equals("UZS")) {
            throw new RuntimeException(messageSource.getMessage("edit_not_permitted_for_sum", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        Currency currency = repo.getReferenceById(item.getId());
        currency.setCurrencyValueInUzs(item.getCurrencyValueInUzs());
    }

    @Override
    public void delete(Long id) {
        Currency currency = repo.getReferenceById(id);
        if (currency.getCurrencyType().getSymbol().equals("UZS")) {
            throw new RuntimeException(messageSource.getMessage("currency_uzs_is_not_deletable", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        repo.deleteById(id);
    }

}
