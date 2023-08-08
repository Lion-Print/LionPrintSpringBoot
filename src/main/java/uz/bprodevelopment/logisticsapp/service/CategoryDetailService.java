package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.CategoryDetailDto;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface CategoryDetailService {

    CategoryDetailDto getOne(Long id);

    List<CategoryDetailDto> getListAll(
            String name,
            Long categoryId,
            String sort
    );

    CustomPage<CategoryDetailDto> getList(
            Integer page,
            Integer size,
            String name,
            Long categoryId,
            String sort
    );

    void save(CategoryDetailDto item);

    void update(CategoryDetailDto item);

    void delete(Long id);

}
