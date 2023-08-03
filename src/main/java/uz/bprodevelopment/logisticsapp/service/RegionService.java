package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.bprodevelopment.logisticsapp.dto.RegionCreationDto;
import uz.bprodevelopment.logisticsapp.entity.Region;

import java.util.List;


public interface RegionService {

    Region getOne(Long id);

    List<Region> getByNameUz(String nameUz);

    List<Region> getListAll(Specification<Region> specification, Sort sorting);

    Page<Region> getList(Specification<Region> specification, Pageable pageable);

    void save(RegionCreationDto item);

    void update(RegionCreationDto item);

    void delete(Long id);

}
