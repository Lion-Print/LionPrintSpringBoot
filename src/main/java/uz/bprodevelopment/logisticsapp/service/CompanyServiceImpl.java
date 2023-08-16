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
import uz.bprodevelopment.logisticsapp.dto.CompanyDto;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.repo.CompanyRepo;
import uz.bprodevelopment.logisticsapp.spec.CompanySpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static uz.bprodevelopment.logisticsapp.base.config.Constants.ROLE_COMPANY_ADMIN;
import static uz.bprodevelopment.logisticsapp.base.config.Constants.ROLE_COMPANY_MANAGER;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo repo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Override
    public CompanyDto getOne(Long id) {
        Company item = repo.getReferenceById(id);
        return item.toDto();
    }

    @Override
    public List<CompanyDto> getListAll(
            String name,
            String director,
            String phone,
            String sort
    ) {
        CompanySpec spec1 = new CompanySpec(new SearchCriteria("id", ">", 0));
        Specification<Company> spec = Specification.where(spec1);

        if (name != null) spec = spec.and(new CompanySpec(new SearchCriteria("name", ":", name)));

        if (director != null) spec = spec.and(new CompanySpec(new SearchCriteria("director", ":", director)));

        if (phone != null) spec = spec.and(new CompanySpec(new SearchCriteria("phone", ":", phone)));

        List<Company> companies = repo.findAll(spec, Sort.by(sort).descending());
        List<CompanyDto> companyDtos = new ArrayList<>();
        companies.forEach(company -> companyDtos.add(company.toDto()));

        return companyDtos;
    }

    @Override
    public CustomPage<CompanyDto> getList(
            Integer page,
            Integer size,
            String name,
            String director,
            String phone,
            Boolean isBlocked,
            String sort
    ) {

        CompanySpec spec1 = new CompanySpec(new SearchCriteria("id", ">", 0));
        Specification<Company> spec = Specification.where(spec1);

        if (name != null) spec = spec.and(new CompanySpec(new SearchCriteria("name", ":", name)));
        if (director != null) spec = spec.and(new CompanySpec(new SearchCriteria("director", ":", director)));
        if (phone != null) spec = spec.and(new CompanySpec(new SearchCriteria("phone", ":", phone)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<Company> responsePage = repo.findAll(spec, pageable);
        List<CompanyDto> dtos = new ArrayList<>();
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
    public void save(CompanyDto item) {
        if (item.getId() != null) throw new RuntimeException(messageSource.getMessage("do_not_send_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getName() == null) throw new RuntimeException(messageSource.getMessage("enter_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getDirector() == null) throw new RuntimeException(messageSource.getMessage("enter_director_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getPhone() == null) throw new RuntimeException(messageSource.getMessage("enter_phone", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUserFullName() == null) throw new RuntimeException(messageSource.getMessage("enter_user_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUsername() == null) throw new RuntimeException(messageSource.getMessage("enter_username", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getPassword() == null) throw new RuntimeException(messageSource.getMessage("enter_password", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (repo.existsByName(item.getName())) throw new RuntimeException(messageSource.getMessage("supplier_is_exist_with_this_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (userRepo.existsByUsername(item.getUsername())) throw new RuntimeException(messageSource.getMessage("username_is_busy", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        Company company = item.toEntity();
        repo.save(company);

        User user = new User();
        user.setFullName(item.getUserFullName());
        user.setUsername(item.getUsername());
        user.setPassword(passwordEncoder.encode(item.getPassword()));
        user.setCompany(company);

        Role role = roleRepo.findByName(ROLE_COMPANY_ADMIN);
        user.getRoles().add(role);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void update(CompanyDto item) {
        if (item.getId() == null) throw new RuntimeException(messageSource.getMessage("do_not_send_id", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getName() == null) throw new RuntimeException(messageSource.getMessage("enter_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getDirector() == null) throw new RuntimeException(messageSource.getMessage("enter_director_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getPhone() == null) throw new RuntimeException(messageSource.getMessage("enter_phone", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUserFullName() == null) throw new RuntimeException(messageSource.getMessage("enter_user_full_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if(item.getUsername() == null) throw new RuntimeException(messageSource.getMessage("enter_username", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        Company dbCompany = repo.getReferenceById(item.getId());
        User user = userRepo.findByUsername(dbCompany.getUsername());

        if (!dbCompany.getName().equals(item.getName()) && repo.existsByName(item.getName()))
            throw new RuntimeException(messageSource.getMessage("company_is_exist_with_this_name", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        if (!dbCompany.getUsername().equals(item.getUsername()) && userRepo.existsByUsername(item.getUsername()))
            throw new RuntimeException(messageSource.getMessage("username_is_busy", null, new Locale(BaseAppUtils.getCurrentLanguage())));

        Company company = item.toEntity();
        repo.save(company);

        user.setFullName(item.getUserFullName());
        user.setUsername(item.getUsername());

        if (item.getPassword() != null && !item.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(item.getPassword()));
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepo.deleteAllByCompanyId(id);
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        if (currentUser == null || currentUser.getCompany() == null) {
            throw new RuntimeException(messageSource.getMessage("yuo_can_not_add_user_to_this_company", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }
        if (userRepo.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException(messageSource.getMessage("username_is_busy", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }

        Company company = new Company();
        company.setId(currentUser.getCompany().getId());

        User user = userDto.toEntity();
        user.setCompany(company);

        Role role = roleRepo.findByName(ROLE_COMPANY_MANAGER);
        user.getRoles().add(role);

        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void blockUser(Long id, Boolean isBlock) {

        User user = userRepo.getReferenceById(id);
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());

        if (user.getCompany() == null
                || currentUser.getCompany() == null
                || currentUser.getRoles().stream().noneMatch(role -> role.getName().equals(ROLE_COMPANY_ADMIN))
                || currentUser.getCompany().getId().intValue() != user.getCompany().getId().intValue()){
            throw new RuntimeException(messageSource.getMessage("you_can_not_block_this_user", null, new Locale(BaseAppUtils.getCurrentLanguage())));
        }

        user.setIsBlocked(isBlock);
    }
}
