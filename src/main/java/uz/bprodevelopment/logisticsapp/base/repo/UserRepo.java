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

    @Query(value = "SELECT u.fcmToken FROM User u LEFT JOIN u.userType ut WHERE u.fcmToken IS NOT NULL and ut.id = ?1")
    List<String> findByUserUserType(Long id);

}
