package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.CompanyDto;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.Company;

import java.util.List;

public interface CompanyService {

    Company getOne(Long id);

    List<Company> getListAll(
            String name,
            String director,
            String phone,
            String sort
    );

    Page<Company> getList(
            Integer page,
            Integer size,
            String name,
            String director,
            String phone,
            String sort
    );

    void save(CompanyDto item);

    void update(CompanyDto item);

    void delete(Long id);

    void addUser(UserDto userDto);
}
