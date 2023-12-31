package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

}
