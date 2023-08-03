package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.bprodevelopment.logisticsapp.entity.CargoStatus;

import java.util.List;


public interface CargoStatusService {

    CargoStatus getOne(Long id);

    List<CargoStatus> getListAll(Specification<CargoStatus> specification, Sort sorting);

    Page<CargoStatus> getList(Specification<CargoStatus> specification, Pageable pageable);

    CargoStatus save(CargoStatus item);

    void delete(Long id);

    CargoStatus getOneByNameUz(String nameUz);


}
