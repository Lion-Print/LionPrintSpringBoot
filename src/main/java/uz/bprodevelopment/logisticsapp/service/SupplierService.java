package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier getOne(Long id);

    List<Supplier> getListAll(
            String name,
            String director,
            String phone,
            String sort
    );

    Page<Supplier> getList(
            Integer page,
            Integer size,
            String name,
            String director,
            String phone,
            String sort
    );

    void save(SupplierDto item);

    void update(SupplierDto item);

    void delete(Long id);

    void addUser(UserDto userDto);

}
