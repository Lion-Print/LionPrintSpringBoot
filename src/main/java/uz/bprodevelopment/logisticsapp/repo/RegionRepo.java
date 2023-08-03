package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Region;

import java.util.List;

public interface RegionRepo extends JpaRepository<Region, Long>,
        JpaSpecificationExecutor<Region> {

    List<Region> findByNameUz(String nameUz);

}
