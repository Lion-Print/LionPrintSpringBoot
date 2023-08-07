package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.entity.CompanyProduct;

import java.util.List;

public interface CompanyProductService {

    CompanyProduct getOne(Long id);

    List<CompanyProduct> getListAll(
            String name,
            String sort
    );

    Page<CompanyProduct> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    );

    void save(CompanyProduct item);

    void update(CompanyProduct item);

    void delete(Long id);
}
