package uz.bprodevelopment.logisticsapp.base.service;


import uz.bprodevelopment.logisticsapp.base.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRole(String name);
    Role getRole(Long id);
    List<Role> getRoles();

    Role saveRole(Role role);
    Role updateRole(Role role);
    void deleteRole(Long id);

}
