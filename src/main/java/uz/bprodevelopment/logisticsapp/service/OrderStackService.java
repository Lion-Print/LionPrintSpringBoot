package uz.bprodevelopment.logisticsapp.service;


import uz.bprodevelopment.logisticsapp.dto.OrderStackDto;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface OrderStackService {
    OrderStackDto getOne(Long id);
    CustomPage<OrderStackDto> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    );
    void delete(Long id);
}
