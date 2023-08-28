package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Order;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    List<Order> findAllByOrderStackId(Long id);

}
