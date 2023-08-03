package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.PaymentType;


public interface PaymentTypeRepo extends JpaRepository<PaymentType, Long>,
        JpaSpecificationExecutor<PaymentType> {

    PaymentType findByNameUz(String nameUz);

}
