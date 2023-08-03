package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.AdminPortion;


public interface AdminPortionRepo extends JpaRepository<AdminPortion, Long>{

    AdminPortion findByCurrencyTypeId(Long currencyTypeId);

}