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
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.entity.Supplier;
import uz.bprodevelopment.logisticsapp.entity.Supplier;
import uz.bprodevelopment.logisticsapp.repo.SupplierRepo;
import uz.bprodevelopment.logisticsapp.repo.SupplierRepo;
import uz.bprodevelopment.logisticsapp.spec.SupplierSpec;
import uz.bprodevelopment.logisticsapp.spec.SupplierSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
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
        if (item.getId() != null) throw new RuntimeException("Taminotchi kompaniya yaratishda id yuborish mumkin emas");

        if(item.getName() == null) throw new RuntimeException("Taminotchi kompaniya nomini kiriting");

        if(item.getDirector() == null) throw new RuntimeException("Taminotchi kompaniya directori ismini kiriting");

        if(item.getPhone() == null) throw new RuntimeException("Taminotchi kompaniya telefon raqamini kiriting");

        if(item.getUserFullName() == null) throw new RuntimeException("Taminotchi kompaniya foydalanuvchisi ismini kiriting");

        if(item.getUsername() == null) throw new RuntimeException("Taminotchi kompaniya foydalanuvchisi uchun username kiriting");

        if(item.getPassword() == null) throw new RuntimeException("Taminotchi kompaniya foydalanuvchisi uchun parol kiriting");

        if (repo.existsByName(item.getName())) throw new RuntimeException("Bunday nom bilan kompaniya yaratilgan");

        if (userRepo.existsByUsername(item.getUsername())) throw new RuntimeException("Bunday username mavjud");

        Supplier company = item.toEntity();
        repo.save(company);

        User user = new User();
        user.setFullName(item.getUserFullName());
        user.setUsername(item.getUsername());
        user.setPassword(passwordEncoder.encode(item.getPassword()));
        user.setSupplier(company);

        Role role = roleRepo.findByName(ROLE_COMPANY_ADMIN);
        user.setRole(role);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void update(SupplierDto item) {
        if (item.getId() == null) throw new RuntimeException("Taminotchi kompaniyani tahrirlashda id yuborish shart");

        if(item.getName() == null) throw new RuntimeException("Taminotchi kompaniya nomini kiriting");

        if(item.getDirector() == null) throw new RuntimeException("Taminotchi kompaniya directori ismini kiriting");

        if(item.getPhone() == null) throw new RuntimeException("Taminotchi kompaniya telefon raqamini kiriting");

        if(item.getUserFullName() == null) throw new RuntimeException("Taminotchi kompaniya foydalanuvchisi ismini kiriting");

        if(item.getUsername() == null) throw new RuntimeException("Taminotchi kompaniya foydalanuvchisi uchun username kiriting");

        Supplier dbSupplier = repo.getReferenceById(item.getId());
        User user = userRepo.findByUsername(dbSupplier.getUsername());

        if (!dbSupplier.getName().equals(item.getName()) && repo.existsByName(item.getName())) throw new RuntimeException("Bunday nom bilan kompaniya yaratilgan");

        if (!dbSupplier.getUsername().equals(item.getUsername()) && userRepo.existsByUsername(item.getUsername())) throw new RuntimeException("Bunday username mavjud");

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
            throw new RuntimeException("Siz bu kompaniyaga foydalanuvchi qo'sholmaysiz");
        }

        Supplier company = new Supplier();
        company.setId(currentUser.getSupplier().getId());

        User user = userDto.toEntity();
        user.setSupplier(company);

        userRepo.save(user);
    }
}
