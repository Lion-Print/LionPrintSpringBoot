package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Cargo;
import uz.bprodevelopment.logisticsapp.entity.CargoStatus;


public interface CargoStatusRepo extends JpaRepository<CargoStatus, Long>,
        JpaSpecificationExecutor<CargoStatus> {

    CargoStatus findByNameUz(String nameUz);

}
