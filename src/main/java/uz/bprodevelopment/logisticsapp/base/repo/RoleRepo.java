package uz.bprodevelopment.logisticsapp.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.bprodevelopment.logisticsapp.base.entity.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
