package uz.bprodevelopment.logisticsapp.base.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface UserService {

    UserDto getOne(Long id);

    List<UserDto> getListAll(
            String username,
            String fullName,
            String sort
    );

    CustomPage<UserDto> getList(
            Integer page,
            Integer size,
            String username,
            String fullName,
            String sort
    );

    void save(UserDto item);

    void update(UserDto item);

    void delete(Long id);

    User getOneByUsername(String username);

}
