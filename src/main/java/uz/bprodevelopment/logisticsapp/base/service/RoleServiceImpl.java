package uz.bprodevelopment.logisticsapp.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.repo.RoleRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;


    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        Role dbRole = roleRepo.getReferenceById(role.getId());
        dbRole.setName(role.getName());
        return dbRole;
    }

    @Override
    public void deleteRole(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public Role getRole(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public Role getRole(Long id) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }
}
