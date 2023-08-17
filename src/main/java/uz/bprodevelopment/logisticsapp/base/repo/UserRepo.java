package uz.bprodevelopment.logisticsapp.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.bprodevelopment.logisticsapp.base.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    User findByUsername(String username);
    boolean existsByUsername(String username);

    void deleteAllByCompanyId(Long companyId);

    void deleteAllBySupplierId(Long companyId);
}
