package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.OrderStack;

public interface OrderStackRepo extends JpaRepository<OrderStack, Long>,
        JpaSpecificationExecutor<OrderStack> {}
