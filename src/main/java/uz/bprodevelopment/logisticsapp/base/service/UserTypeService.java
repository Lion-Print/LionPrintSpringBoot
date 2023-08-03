package uz.bprodevelopment.logisticsapp.base.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.UserType;

import java.util.List;

public interface UserTypeService {

    UserType getOne(Long id);

    UserType getOneByNameUz(String nameUz);

    List<UserType> getListAll(Specification<UserType> specification, Sort sorting);

    Page<UserType> getList(Specification<UserType> specification, Pageable pageable);

    UserType save(UserType item);

    void delete(Long id);

}
