package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Cargo;

import java.util.List;


public interface CargoRepo extends JpaRepository<Cargo, Long>,
        JpaSpecificationExecutor<Cargo> { }
