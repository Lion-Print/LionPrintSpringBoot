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
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.CompanyDto;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.repo.CompanyRepo;
import uz.bprodevelopment.logisticsapp.spec.CompanySpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo repo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Company getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Company> getListAll(
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

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<Company> getList(
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

        return repo.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public void save(CompanyDto item) {
        if(item.getName() == null) {
            throw new RuntimeException("Kompaniya nomini kiriting");
        }
        if(item.getDirector() == null) {
            throw new RuntimeException("Kompaniya directori ismini kiriting");
        }
        if(item.getPhone() == null) {
            throw new RuntimeException("Kompaniya telefon raqamini kiriting");
        }
        if(item.getFullName() == null) {
            throw new RuntimeException("Kompaniya foydalanuvchisi ismini kiriting");
        }
        if(item.getUsername() == null) {
            throw new RuntimeException("Kompaniya foydalanuvchisi uchun username kiriting");
        }
        if(item.getPassword() == null) {
            throw new RuntimeException("Kompaniya foydalanuvchisi uchun parol kiriting");
        }

        Company dbCompany = repo.findByName(item.getName());
        if (dbCompany != null) {
            throw new RuntimeException("Bunday nom bilan kompaniya yaratilgan");
        }

        Company company = item.toEntity();
        repo.save(company);

        Role role = new Role();
        role.setId(1L);

        User user = new User();
        user.setFullName(item.getFullName());
        user.setUsername(item.getUsername());
        user.setPassword(passwordEncoder.encode(item.getPassword()));

        user.setCompany(company);
        user.setRole(role);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void update(CompanyDto item) {
        repo.save(item.toEntity());
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
