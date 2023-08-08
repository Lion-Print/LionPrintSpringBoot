package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.CategoryDetailDto;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;

import java.util.List;

public interface CategoryDetailService {

    CategoryDetail getOne(Long id);

    List<CategoryDetail> getListAll(
            String name,
            Long categoryId,
            String sort
    );

    Page<CategoryDetail> getList(
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
