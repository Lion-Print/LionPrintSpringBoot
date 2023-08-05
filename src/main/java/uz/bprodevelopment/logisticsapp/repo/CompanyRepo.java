package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, Long>,
        JpaSpecificationExecutor<Company> {

    Company findByName(String name);

}
