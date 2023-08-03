package uz.bprodevelopment.logisticsapp.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.entity.UserType;

public interface UserTypeRepo extends JpaRepository<UserType, Long>,
        JpaSpecificationExecutor<UserType> {

    UserType findByNameUz(String nameUz);

}
