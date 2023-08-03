package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;

import java.util.List;


public interface CurrencyTypeService {

    CurrencyType getOne(Long id);

    List<CurrencyType> getListAll(Specification<CurrencyType> specification, Sort sorting);

    Page<CurrencyType> getList(Specification<CurrencyType> specification, Pageable pageable);

    CurrencyType save(CurrencyType item);

    void delete(Long id);

    CurrencyType getOneByNameUz(String nameUz);
}
