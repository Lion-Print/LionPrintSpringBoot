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
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.CurrencyTypeDto;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;
import uz.bprodevelopment.logisticsapp.repo.CurrencyTypeRepo;
import uz.bprodevelopment.logisticsapp.spec.CurrencyTypeSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyTypeServiceImpl implements CurrencyTypeService {

    private final CurrencyTypeRepo repo;
    private final MessageSource messageSource;

    @Override
    public CurrencyTypeDto getOne(Long id) {
        CurrencyType category = repo.findById(id).get();
        return category.toDto();
    }


    @Override
    public List<CurrencyTypeDto> getListAll(
            String name,
            String sort
    ) {
        CurrencyTypeSpec spec1 = new CurrencyTypeSpec(new SearchCriteria("id", ">", 0));
        Specification<CurrencyType> spec = Specification.where(spec1);

        if (name != null) {
            Specification<CurrencyType> spec2 = Specification.where(new CurrencyTypeSpec(new SearchCriteria("nameUz", ":", name)));
            spec2 = spec2.or(new CurrencyTypeSpec(new SearchCriteria("nameRu", ":", name)));
            spec = spec.and(spec2);
        }

        List<CurrencyType> categories = repo.findAll(spec, Sort.by(sort).descending());
        List<CurrencyTypeDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> categoryDtos.add(category.toDto()));

        return categoryDtos;
    }

    @Override
    public CustomPage<CurrencyTypeDto> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    ) {

        CurrencyTypeSpec spec1 = new CurrencyTypeSpec(new SearchCriteria("id", ">", 0));
        Specification<CurrencyType> spec = Specification.where(spec1);

        if (name != null) {
            Specification<CurrencyType> spec2 = Specification.where(new CurrencyTypeSpec(new SearchCriteria("nameUz", ":", name)));
            spec2 = spec2.or(new CurrencyTypeSpec(new SearchCriteria("nameRu", ":", name)));
            spec = spec.and(spec2);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<CurrencyType> responsePage = repo.findAll(spec, pageable);
        List<CurrencyTypeDto> dtos = new ArrayList<>();
        responsePage.getContent().forEach(category -> dtos.add(category.toDto()));


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
    public void save(CurrencyTypeDto item) {
        if(item.getNameUz() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_name_uz", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getNameRu() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_name_ru", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getSymbol() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_symbol", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if (item.getId() != null) {
            throw new RuntimeException(messageSource.getMessage("do_not_send_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }

        CurrencyType category = item.toEntity();
        repo.save(category);
    }

    @Override
    public void update(CurrencyTypeDto item) {
        if(item.getNameUz() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_name_uz", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getNameRu() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_name_ru", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if(item.getSymbol() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_symbol", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if (item.getId() == null) {
            throw new RuntimeException(messageSource.getMessage("enter_valid_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        CurrencyType currencyType = item.toEntity();
        repo.save(currencyType);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
