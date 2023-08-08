package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.ProductDto;
import uz.bprodevelopment.logisticsapp.entity.Product;
import uz.bprodevelopment.logisticsapp.entity.ProductDetail;

import java.util.List;

public interface ProductService {

    Product getOne(Long id);

    List<ProductDto> getListAll(
            String name,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long companyId,
            Long supplierId,
            String sort
    );

    Page<ProductDto> getList(
            Integer page,
            Integer size,
            String name,
            Double price,
            Integer hasDelivery,
            Integer hasNds,
            Long categoryId,
            Long companyId,
            Long supplierId,
            String sort
    );

    void save(ProductDto item);

    void update(ProductDto item);

    void delete(Long id);

}
