package uz.bprodevelopment.logisticsapp.base.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.dto.UserDto;

import java.util.List;

public interface UserService {

    User getOne(Long id);

    List<User> getListAll(
            String username,
            String fullName,
            Long userTypeId,
            String sort
    );

    Page<User> getList(
            Integer page,
            Integer size,
            String username,
            String fullName,
            Long userTypeId,
            String sort
    );

    void save(UserDto item);

    void update(UserDto item);

    User getOneByUsername(String username);

}
