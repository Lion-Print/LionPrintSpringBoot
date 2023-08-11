package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.repo.RoleRepo;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.entity.Supplier;
import uz.bprodevelopment.logisticsapp.repo.SupplierRepo;
import uz.bprodevelopment.logisticsapp.spec.SupplierSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static uz.bprodevelopment.logisticsapp.base.config.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepo repo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Override
    public SupplierDto getOne(Long id) {
        Supplier item = repo.getReferenceById(id);
        return item.toDto();
    }

    @Override
    public List<SupplierDto> getListAll(
            String name,
            String director,
            String phone,
            String sort
    ) {
        SupplierSpec spec1 = new SupplierSpec(new SearchCriteria("id", ">", 0));
        Specification<Supplier> spec = Specification.where(spec1);

        if (name != null) spec = spec.and(new SupplierSpec(new SearchCriteria("name", ":", name)));

        if (director != null) spec = spec.and(new SupplierSpec(new SearchCriteria("director", ":", director)));

        if (phone != null) spec = spec.and(new SupplierSpec(new SearchCriteria("phone", ":", phone)));

        List<Supplier> companies = repo.findAll(spec, Sort.by(sort).descending());
        List<SupplierDto> companyDtos = new ArrayList<>();
        companies.forEach(company -> companyDtos.add(company.toDto()));

        return companyDtos;
    }

    @Override
    public CustomPage<SupplierDto> getList(
            Integer page,
            Integer size,
            String name,
            String director,
            String phone,
            Boolean isBlocked,
            String sort
    ) {

        SupplierSpec spec1 = new SupplierSpec(new SearchCriteria("id", ">", 0));
        Specification<Supplier> spec = Specification.where(spec1);

        if (name != null) spec = spec.and(new SupplierSpec(new SearchCriteria("name", ":", name)));
        if (director != null) spec = spec.and(new SupplierSpec(new SearchCriteria("director", ":", director)));
        if (phone != null) spec = spec.and(new SupplierSpec(new SearchCriteria("phone", ":", phone)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<Supplier> responsePage = repo.findAll(spec, pageable);
        List<SupplierDto> dtos = new ArrayList<>();
        responsePage.getContent().forEach(company -> dtos.add(company.toDto()));

        return new CustomPage<>(
                dtos,
                responsePage.isFirst(),
                responsePage.isLast(),
                responsePage.getNumber(),
                responsePage.getTotalPages(),
                responsePage.getTotalElements()
        );

    }

    @Override
    @Transactional
    public void save(SupplierDto item) {
        if (item.getId() != null) throw new RuntimeException(messageSource.getMessage("do_not_send_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getName() == null) throw new RuntimeException(messageSource.getMessage("enter_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getDirector() == null) throw new RuntimeException(messageSource.getMessage("enter_director_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getPhone() == null) throw new RuntimeException(messageSource.getMessage("enter_phone", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUserFullName() == null) throw new RuntimeException(messageSource.getMessage("enter_user_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUsername() == null) throw new RuntimeException(messageSource.getMessage("enter_username", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getPassword() == null) throw new RuntimeException(messageSource.getMessage("enter_password", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (repo.existsByName(item.getName())) throw new RuntimeException(messageSource.getMessage("supplier_is_exist_with_this_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (userRepo.existsByUsername(item.getUsername())) throw new RuntimeException(messageSource.getMessage("username_is_busy", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        Supplier company = item.toEntity();
        repo.save(company);

        User user = new User();
        user.setFullName(item.getUserFullName());
        user.setUsername(item.getUsername());
        user.setPassword(passwordEncoder.encode(item.getPassword()));
        user.setSupplier(company);

        Role role = roleRepo.findByName(ROLE_SUPPLIER_ADMIN);
        user.getRoles().add(role);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void update(SupplierDto item) {
        if (item.getId() == null) throw new RuntimeException(messageSource.getMessage("do_not_send_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getName() == null) throw new RuntimeException(messageSource.getMessage("enter_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getDirector() == null) throw new RuntimeException(messageSource.getMessage("enter_director_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getPhone() == null) throw new RuntimeException(messageSource.getMessage("enter_phone", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUserFullName() == null) throw new RuntimeException(messageSource.getMessage("enter_user_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUsername() == null) throw new RuntimeException(messageSource.getMessage("enter_username", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        Supplier dbSupplier = repo.getReferenceById(item.getId());
        User user = userRepo.findByUsername(dbSupplier.getUsername());

        if (!dbSupplier.getName().equals(item.getName()) && repo.existsByName(item.getName()))
            throw new RuntimeException(messageSource.getMessage("supplier_is_exist_with_this_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (!dbSupplier.getUsername().equals(item.getUsername()) && userRepo.existsByUsername(item.getUsername()))
            throw new RuntimeException(messageSource.getMessage("username_is_busy", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        Supplier company = item.toEntity();
        repo.save(company);

        user.setFullName(item.getUserFullName());
        user.setUsername(item.getUsername());

        if (item.getPassword() != null && !item.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(item.getPassword()));
        }
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void addUser(UserDto userDto) {
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        if (currentUser == null || currentUser.getSupplier() == null) {
            throw new RuntimeException(messageSource.getMessage("yuo_can_not_add_user_to_this_company", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }

        Supplier company = new Supplier();
        company.setId(currentUser.getSupplier().getId());

        User user = userDto.toEntity();
        user.setSupplier(company);

        userRepo.save(user);
    }
}
