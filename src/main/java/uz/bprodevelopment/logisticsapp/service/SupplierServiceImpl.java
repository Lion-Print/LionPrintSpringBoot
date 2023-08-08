package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.repo.RoleRepo;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Supplier;
import uz.bprodevelopment.logisticsapp.repo.SupplierRepo;
import uz.bprodevelopment.logisticsapp.spec.SupplierSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import java.util.List;

import static uz.bprodevelopment.logisticsapp.base.config.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepo repo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Supplier getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Supplier> getListAll(
            String name,
            String director,
            String phone,
            String sort
    ) {
        SupplierSpec spec1 = new SupplierSpec(new SearchCriteria("id", ">", 0));
        Specification<Supplier> spec = Specification.where(spec1);

        if (name != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("name", ":", name)));
        if (director != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("director", ":", director)));
        if (phone != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("phone", ":", phone)));

        User user = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        if (user.getRole() == null) {
            throw new RuntimeException("User role mavjud emas");
        }

        String roleName = user.getRole().getName();
        if (!roleName.equals(ROLE_ADMIN) && !roleName.equals(ROLE_MANAGER)) {
            Company company = user.getCompany();
            if (company == null) {
                throw new RuntimeException("User kompaniyasi mavjud emas");
            }
            Long companyId = company.getId();
            spec = spec.and(new SupplierSpec(new SearchCriteria("companyId", ":", companyId)));
        }

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<Supplier> getList(
            Integer page,
            Integer size,
            String name,
            String director,
            String phone,
            String sort
    ) {

        SupplierSpec spec1 = new SupplierSpec(new SearchCriteria("id", ">", 0));
        Specification<Supplier> spec = Specification.where(spec1);

        if (name != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("name", ":", name)));
        if (director != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("director", ":", director)));
        if (phone != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("phone", ":", phone)));

        User user = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        if (user.getRole() == null) {
            throw new RuntimeException("User role mavjud emas");
        }

        String roleName = user.getRole().getName();
        if (!roleName.equals(ROLE_ADMIN) && !roleName.equals(ROLE_MANAGER)) {
            Company company = user.getCompany();
            if (company == null) {
                throw new RuntimeException("User kompaniyasi mavjud emas");
            }
            Long companyId = company.getId();
            spec = spec.and(new SupplierSpec(new SearchCriteria("companyId", ":", companyId)));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public void save(SupplierDto item) {

        if (item.getName() == null) {
            throw new RuntimeException("Kompaniya nomini kiriting");
        }
        if (item.getDirector() == null) {
            throw new RuntimeException("Kompaniya directori ismini kiriting");
        }
        if (item.getPhone() == null) {
            throw new RuntimeException("Kompaniya telefon raqamini kiriting");
        }
        if (item.getFullName() == null) {
            throw new RuntimeException("Kompaniya foydalanuvchisi ismini kiriting");
        }
        if (item.getUsername() == null) {
            throw new RuntimeException("Kompaniya foydalanuvchisi uchun username kiriting");
        }
        if (item.getPassword() == null) {
            throw new RuntimeException("Kompaniya foydalanuvchisi uchun parol kiriting");
        }

        Role role = roleRepo.findByName(ROLE_SUPPLIER_ADMIN);
        if (role == null) {
            throw new RuntimeException("ROLE_SUPPLIER_ADMIN mavjud emas");
        }

        if (userRepo.findByUsername(item.getUsername()) != null) {
            throw new RuntimeException("Bunday username mavjud");
        }

        Supplier supplier = item.toEntity();
        supplier.getCompanies().add(new Company(item.getCompanyId()));

        repo.save(supplier);

        User user = new User();
        user.setFullName(item.getFullName());
        user.setUsername(item.getUsername());
        user.setPassword(passwordEncoder.encode(item.getPassword()));

        user.setSupplier(supplier);
        user.setRole(role);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void update(SupplierDto item) {
        repo.save(item.toEntity());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void addUser(UserDto userDto) {
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        if (currentUser == null || currentUser.getSupplier() == null) {
            throw new RuntimeException("Siz bu kompaniyaga foydalanuvchi qo'sholmaysiz");
        }

        Supplier supplier = new Supplier();
        supplier.setId(currentUser.getSupplier().getId());

        User user = userDto.toEntity();
        user.setSupplier(supplier);

        userRepo.save(user);
    }
}
