package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.dto.CategoryDto;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.repo.CategoryRepo;
import uz.bprodevelopment.logisticsapp.spec.CategorySpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo repo;

    @Override
    public Category getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Category> getListAll(
            String name,
            String sort
    ) {
        CategorySpec spec1 = new CategorySpec(new SearchCriteria("id", ">", 0));
        Specification<Category> spec = Specification.where(spec1);

        if (name != null) {
            Specification<Category> spec2 = Specification.where(new CategorySpec(new SearchCriteria("nameUz", ":", name)));
            spec2 = spec2.or(new CategorySpec(new SearchCriteria("nameRu", ":", name)));
            spec = spec.and(spec2);
        }

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<Category> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    ) {

        CategorySpec spec1 = new CategorySpec(new SearchCriteria("id", ">", 0));
        Specification<Category> spec = Specification.where(spec1);

        if (name != null) {
            Specification<Category> spec2 = Specification.where(new CategorySpec(new SearchCriteria("nameUz", ":", name)));
            spec2 = spec2.or(new CategorySpec(new SearchCriteria("nameRu", ":", name)));
            spec = spec.and(spec2);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    public void save(CategoryDto item) {
        if(item.getNameUz() == null) {
            throw new RuntimeException("Uzbekcha nomini kiriting");
        }
        if(item.getNameRu() == null) {
            throw new RuntimeException("Ruscha nomini kiriting");
        }

        Category category = item.toEntity();
        repo.save(category);
    }

    @Override
    public void update(CategoryDto item) {
        if(item.getNameUz() == null) {
            throw new RuntimeException("Uzbekcha nomini kiriting");
        }
        if(item.getNameRu() == null) {
            throw new RuntimeException("Ruscha nomini kiriting");
        }
        Category category = item.toEntity();
        repo.save(category);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
