package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.bprodevelopment.logisticsapp.entity.HalfCargoType;

import java.util.List;


public interface HalfCargoTypeService {

    HalfCargoType getOne(Long id);

    List<HalfCargoType> getListAll(Specification<HalfCargoType> specification, Sort sorting);

    Page<HalfCargoType> getList(Specification<HalfCargoType> specification, Pageable pageable);

    HalfCargoType save(HalfCargoType item);

    void delete(Long id);

    HalfCargoType getOneByNameUz(String nameUz);
}
