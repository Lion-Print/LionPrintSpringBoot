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
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo repo;

    @Override
    public CategoryDto getOne(Long id) {
        Category category = repo.findById(id).get();
        return category.toDto();
    }

    @Override
    public List<CategoryDto> getListAll(
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

        List<Category> categories = repo.findAll(spec, Sort.by(sort).descending());
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> categoryDtos.add(category.toDto()));

        return categoryDtos;
    }

    @Override
    public CustomPage<CategoryDto> getList(
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
        Page<Category> responsePage = repo.findAll(spec, pageable);
        List<CategoryDto> dtos = new ArrayList<>();
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
        if (item.getId() == null) {
            throw new RuntimeException("ID kiritilmagan");
        }
        Category category = item.toEntity();
        repo.save(category);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
