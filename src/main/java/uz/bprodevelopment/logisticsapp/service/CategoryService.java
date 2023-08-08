package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.CategoryDto;
import uz.bprodevelopment.logisticsapp.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getOne(Long id);

    List<Category> getListAll(
            String name,
            String sort
    );

    Page<Category> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    );

    void save(CategoryDto item);

    void update(CategoryDto item);

    void delete(Long id);

}
