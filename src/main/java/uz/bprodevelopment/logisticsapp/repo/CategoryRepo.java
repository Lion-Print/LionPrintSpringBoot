package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>,
        JpaSpecificationExecutor<Category> {

    boolean existsByNameUz(String name);

}
