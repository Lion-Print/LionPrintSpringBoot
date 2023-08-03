package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long>,
        JpaSpecificationExecutor<Payment> {}
