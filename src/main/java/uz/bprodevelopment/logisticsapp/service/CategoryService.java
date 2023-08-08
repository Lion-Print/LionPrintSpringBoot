package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.CategoryDto;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface CategoryService {

    CategoryDto getOne(Long id);

    List<CategoryDto> getListAll(
            String name,
            String sort
    );

    CustomPage<CategoryDto> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    );

    void save(CategoryDto item);

    void update(CategoryDto item);

    void delete(Long id);

}
