package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.ProductDto;
import uz.bprodevelopment.logisticsapp.entity.Product;
import uz.bprodevelopment.logisticsapp.entity.ProductDetail;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface ProductService {

    ProductDto getOne(Long id);

    List<ProductDto> getListAll(
            String name,
            String description,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long supplierId,
            String sort,
            Boolean descending
    );

    CustomPage<ProductDto> getList(
            Integer page,
            Integer size,
            String name,
            String description,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long supplierId,
            String sort,
            Boolean descending
    );

    void save(ProductDto item);

    void update(ProductDto item);

    void delete(Long id);

}
