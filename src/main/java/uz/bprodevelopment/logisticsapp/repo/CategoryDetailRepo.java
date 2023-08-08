package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;

public interface CategoryDetailRepo extends JpaRepository<CategoryDetail, Long>,
        JpaSpecificationExecutor<CategoryDetail> {
}
