package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.dto.CategoryDetailDto;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;
import uz.bprodevelopment.logisticsapp.repo.CategoryDetailRepo;
import uz.bprodevelopment.logisticsapp.spec.CategoryDetailSpec;
import uz.bprodevelopment.logisticsapp.spec.CategorySpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryDetailServiceImpl implements CategoryDetailService {

    private final CategoryDetailRepo repo;

    @Override
    public CategoryDetail getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<CategoryDetail> getListAll(
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
        if (categoryId != null) {
            spec = spec.and(new CategoryDetailSpec(new SearchCriteria("categoryId", ":", categoryId)));
        }

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<CategoryDetail> getList(
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

        if (categoryId != null) {
            spec = spec.and(new CategoryDetailSpec(new SearchCriteria("categoryId", ":", categoryId)));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    public void save(CategoryDetailDto item) {
        if(item.getNameUz() == null) {
            throw new RuntimeException("Uzbekcha nomini kiriting");
        }
        if(item.getNameRu() == null) {
            throw new RuntimeException("Ruscha nomini kiriting");
        }

        CategoryDetail category = item.toEntity();
        repo.save(category);
    }

    @Override
    public void update(CategoryDetailDto item) {
        if(item.getNameUz() == null) {
            throw new RuntimeException("Uzbekcha nomini kiriting");
        }
        if(item.getNameRu() == null) {
            throw new RuntimeException("Ruscha nomini kiriting");
        }
        CategoryDetail category = item.toEntity();
        repo.save(category);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
