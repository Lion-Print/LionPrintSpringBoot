package uz.bprodevelopment.logisticsapp.service;


import uz.bprodevelopment.logisticsapp.dto.OrderDto;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface OrderService {

    OrderDto getOne(Long id);

    CustomPage<OrderDto> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    );

    void save(OrderDto item);

    void update(OrderDto item);

    void delete(Long id);

    void changeStatus(Long id, Integer status);
}
