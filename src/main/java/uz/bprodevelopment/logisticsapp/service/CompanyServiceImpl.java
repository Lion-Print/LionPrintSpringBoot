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

import static uz.bprodevelopment.logisticsapp.base.config.Constants.ROLE_COMPANY_ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo repo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

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
            String sort
    ) {

        CompanySpec spec1 = new CompanySpec(new SearchCriteria("id", ">", 0));
        Specification<Company> spec = Specification.where(spec1);

        if (name != null) {
            spec = spec.and(new CompanySpec(new SearchCriteria("name", ":", name)));
        } else if (director != null)
            spec = spec.and(new CompanySpec(new SearchCriteria("director", ":", director)));
        else if (phone != null)
            spec = spec.and(new CompanySpec(new SearchCriteria("phone", ":", phone)));

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
        if (item.getId() != null) throw new RuntimeException("Kompaniya yaratishda id yuborish mumkin emas");

        if(item.getName() == null) throw new RuntimeException("Kompaniya nomini kiriting");

        if(item.getDirector() == null) throw new RuntimeException("Kompaniya directori ismini kiriting");

        if(item.getPhone() == null) throw new RuntimeException("Kompaniya telefon raqamini kiriting");

        if(item.getUserFullName() == null) throw new RuntimeException("Kompaniya foydalanuvchisi ismini kiriting");

        if(item.getUsername() == null) throw new RuntimeException("Kompaniya foydalanuvchisi uchun username kiriting");

        if(item.getPassword() == null) throw new RuntimeException("Kompaniya foydalanuvchisi uchun parol kiriting");

        if (repo.existsByName(item.getName())) throw new RuntimeException("Bunday nom bilan kompaniya yaratilgan");

        if (userRepo.existsByUsername(item.getUsername())) throw new RuntimeException("Bunday username mavjud");

        Company company = item.toEntity();
        repo.save(company);

        User user = new User();
        user.setFullName(item.getUserFullName());
        user.setUsername(item.getUsername());
        user.setPassword(passwordEncoder.encode(item.getPassword()));
        user.setCompany(company);

        Role role = roleRepo.findByName(ROLE_COMPANY_ADMIN);
        user.setRole(role);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void update(CompanyDto item) {
        if (item.getId() == null) throw new RuntimeException("Kompaniyani tahrirlashda id yuborish shart");

        if(item.getName() == null) throw new RuntimeException("Kompaniya nomini kiriting");

        if(item.getDirector() == null) throw new RuntimeException("Kompaniya directori ismini kiriting");

        if(item.getPhone() == null) throw new RuntimeException("Kompaniya telefon raqamini kiriting");

        if(item.getUserFullName() == null) throw new RuntimeException("Kompaniya foydalanuvchisi ismini kiriting");

        if(item.getUsername() == null) throw new RuntimeException("Kompaniya foydalanuvchisi uchun username kiriting");

        Company dbCompany = repo.getReferenceById(item.getId());
        User user = userRepo.findByUsername(dbCompany.getUsername());

        if (!dbCompany.getName().equals(item.getName()) && repo.existsByName(item.getName())) throw new RuntimeException("Bunday nom bilan kompaniya yaratilgan");

        if (!dbCompany.getUsername().equals(item.getUsername()) && userRepo.existsByUsername(item.getUsername())) throw new RuntimeException("Bunday username mavjud");

        Company company = item.toEntity();
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
        if (currentUser == null || currentUser.getCompany() == null) {
            throw new RuntimeException("Siz bu kompaniyaga foydalanuvchi qo'sholmaysiz");
        }

        Company company = new Company();
        company.setId(currentUser.getCompany().getId());

        User user = userDto.toEntity();
        user.setCompany(company);

        userRepo.save(user);
    }
}
