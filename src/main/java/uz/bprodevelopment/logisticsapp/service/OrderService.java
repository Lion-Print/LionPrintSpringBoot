package uz.bprodevelopment.logisticsapp.service;


import uz.bprodevelopment.logisticsapp.dto.OrderDto;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface OrderService {

    Order getOne(Long id);

    List<Order> getListAll(
            Long productId,
            String sort
    );

    CustomPage<Order> getList(
            Integer page,
            Integer size,
            Long productId,
            String sort
    );

    void save(OrderDto item);

    void update(OrderDto item);

    void delete(Long id);

}
