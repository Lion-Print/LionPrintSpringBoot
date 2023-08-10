package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.entity.Supplier;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface SupplierService {

    SupplierDto getOne(Long id);

    List<SupplierDto> getListAll(
            String name,
            String director,
            String phone,
            String sort
    );

    CustomPage<SupplierDto> getList(
            Integer page,
            Integer size,
            String name,
            String director,
            String phone,
            Boolean isBlocked,
            String sort
    );

    void save(SupplierDto item);

    void update(SupplierDto item);

    void delete(Long id);

    void addUser(UserDto userDto);

}
