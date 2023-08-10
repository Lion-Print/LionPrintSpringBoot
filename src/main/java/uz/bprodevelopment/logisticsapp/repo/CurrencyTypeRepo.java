package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;

public interface CurrencyTypeRepo extends JpaRepository<CurrencyType, Long>,
        JpaSpecificationExecutor<CurrencyType> {
}
