package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.ProductDetail;

import java.util.List;

public interface ProductDetailRepo extends JpaRepository<ProductDetail, Long>,
        JpaSpecificationExecutor<ProductDetail> {

    void deleteByProductId(Long productId);

    List<ProductDetail> findByProductId(Long productId);

}
