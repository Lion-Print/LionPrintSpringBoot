package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.bprodevelopment.logisticsapp.entity.TruckType;

import java.util.List;


public interface TruckTypeService {

    TruckType getOne(Long id);

    List<TruckType> getListAll(Specification<TruckType> specification, Sort sorting);

    Page<TruckType> getList(Specification<TruckType> specification, Pageable pageable);

    TruckType save(TruckType item);

    void delete(Long id);

    TruckType getOneByNameUz(String nameUz);
}
