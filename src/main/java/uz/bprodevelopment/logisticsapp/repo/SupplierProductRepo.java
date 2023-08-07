package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.SupplierProduct;

public interface SupplierProductRepo extends JpaRepository<SupplierProduct, Long>,
        JpaSpecificationExecutor<SupplierProduct> {
}
