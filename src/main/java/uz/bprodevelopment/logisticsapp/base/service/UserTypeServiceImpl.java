package uz.bprodevelopment.logisticsapp.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.UserType;
import uz.bprodevelopment.logisticsapp.base.repo.RoleRepo;
import uz.bprodevelopment.logisticsapp.base.repo.UserTypeRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepo repo;

    @Override
    public UserType getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public UserType getOneByNameUz(String nameUz) {
        return repo.findByNameUz(nameUz);
    }

    @Override
    public List<UserType> getListAll(Specification<UserType> specification, Sort sorting) {
        return repo.findAll(specification, sorting);
    }

    @Override
    public Page<UserType> getList(Specification<UserType> specification, Pageable pageable) {
        return repo.findAll(specification, pageable);
    }

    @Override
    public UserType save(UserType item) {
        return repo.save(item);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
