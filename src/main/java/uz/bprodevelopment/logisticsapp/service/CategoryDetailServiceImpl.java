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
import uz.bprodevelopment.logisticsapp.dto.CategoryDetailDto;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;
import uz.bprodevelopment.logisticsapp.repo.CategoryDetailRepo;
import uz.bprodevelopment.logisticsapp.spec.CategoryDetailSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryDetailServiceImpl implements CategoryDetailService {

    private final CategoryDetailRepo repo;
    private final MessageSource messageSource;

    @Override
    public CategoryDetailDto getOne(Long id) {
        CategoryDetail item = repo.getReferenceById(id);
        return item.toDto();
    }

    @Override
    public List<CategoryDetailDto> getListAll(
            String name,
            Long categoryId,
            String sort
    ) {
        CategoryDetailSpec spec1 = new CategoryDetailSpec(new SearchCriteria("id", ">", 0));
        Specification<CategoryDetail> spec = Specification.where(spec1);

        if (name != null) {
            Specification<CategoryDetail> spec2 = Specification.where(new CategoryDetailSpec(new SearchCriteria("nameUz", ":", name)));
            spec2 = spec2.or(new CategoryDetailSpec(new SearchCriteria("nameRu", ":", name)));
            spec = spec.and(spec2);
        }
        if (categoryId != null) spec = spec.and(new CategoryDetailSpec(new SearchCriteria("categoryId", ":", categoryId)));

        List<CategoryDetail> items = repo.findAll(spec, Sort.by(sort).descending());
        List<CategoryDetailDto> dtos = new ArrayList<>();
        items.forEach(item -> dtos.add(item.toDto()));

        return dtos;
    }

    @Override
    public CustomPage<CategoryDetailDto> getList(
            Integer page,
            Integer size,
            String name,
            Long categoryId,
            String sort
    ) {

        CategoryDetailSpec spec1 = new CategoryDetailSpec(new SearchCriteria("id", ">", 0));
        Specification<CategoryDetail> spec = Specification.where(spec1);

        if (name != null) {
            Specification<CategoryDetail> spec2 = Specification.where(new CategoryDetailSpec(new SearchCriteria("nameUz", ":", name)));
            spec2 = spec2.or(new CategoryDetailSpec(new SearchCriteria("nameRu", ":", name)));
            spec = spec.and(spec2);
        }

        if (categoryId != null) spec = spec.and(new CategoryDetailSpec(new SearchCriteria("categoryId", ":", categoryId)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<CategoryDetail> responsePage = repo.findAll(spec, pageable);
        List<CategoryDetailDto> dtos = new ArrayList<>();
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
    public void save(CategoryDetailDto item) {
        if (item.getNameUz() == null) throw new RuntimeException(messageSource.getMessage("enter_name_uz", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        if (item.getNameRu() == null) throw new RuntimeException(messageSource.getMessage("enter_name_ru", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        if (item.getCategoryId() == null) throw new RuntimeException(messageSource.getMessage("enter_category", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        CategoryDetail category = item.toEntity();
        repo.save(category);
    }

    @Override
    public void update(CategoryDetailDto item) {
        if (item.getNameUz() == null) throw new RuntimeException(messageSource.getMessage("enter_name_uz", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        if (item.getNameRu() == null) throw new RuntimeException(messageSource.getMessage("enter_name_ru", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        if (item.getCategoryId() == null) throw new RuntimeException(messageSource.getMessage("enter_category", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        if (item.getId() == null) throw new RuntimeException(messageSource.getMessage("do_not_send_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        CategoryDetail categoryDetail = item.toEntity();
        repo.save(categoryDetail);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
