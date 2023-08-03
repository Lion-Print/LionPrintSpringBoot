package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.TruckType;


public interface TruckTypeRepo extends JpaRepository<TruckType, Long>,
        JpaSpecificationExecutor<TruckType> {

    TruckType findByNameUz(String nameUz);

}