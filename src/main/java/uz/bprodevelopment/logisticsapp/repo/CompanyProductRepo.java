package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.CompanyProduct;

public interface CompanyProductRepo extends JpaRepository<CompanyProduct, Long>,
        JpaSpecificationExecutor<CompanyProduct> {
}
