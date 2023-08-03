package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.HalfCargoType;


public interface HalfCargoTypeRepo extends JpaRepository<HalfCargoType, Long>,
        JpaSpecificationExecutor<HalfCargoType> {

    HalfCargoType findByNameUz(String nameUz);

}