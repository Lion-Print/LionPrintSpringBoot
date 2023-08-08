package uz.bprodevelopment.logisticsapp.base.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.spec.UserSpec;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getOne(Long id) {
        User item = repo.getReferenceById(id);
        return item.toDto();
    }

    @Override
    public List<UserDto> getListAll(
            String username,
            String fullName,
            String sort
    ) {
        UserSpec spec1 = new UserSpec(new SearchCriteria("id", ">", 0));
        Specification<User> spec = Specification.where(spec1);

        if (fullName != null)
            spec = spec.and(new UserSpec(new SearchCriteria("fullName", ":", fullName)));
        if (username != null)
            spec = spec.and(new UserSpec(new SearchCriteria("username", ":", username)));

        List<User> users = repo.findAll(spec, Sort.by(sort).descending());
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(user.toDto()));

        return userDtos;
    }

    @Override
    public CustomPage<UserDto> getList(
            Integer page,
            Integer size,
            String username,
            String fullName,
            String sort
    ) {
        UserSpec spec1 = new UserSpec(new SearchCriteria("id", ">", 0));
        Specification<User> spec = Specification.where(spec1);

        if (fullName != null)
            spec = spec.and(new UserSpec(new SearchCriteria("fullName", ":", fullName)));
        if (username != null)
            spec = spec.and(new UserSpec(new SearchCriteria("username", ":", username)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<User> responsePage = repo.findAll(spec, pageable);
        List<UserDto> dtos = new ArrayList<>();

        responsePage.getContent().forEach(user -> dtos.add(user.toDto()));

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
    public void save(UserDto item) {
        User dbUser = repo.findByUsername(item.getUsername());
        if (item.getId() == null && dbUser != null) {
            throw new RuntimeException("Bu username band: " + item.getUsername());
        }

        User user = item.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    @Override
    @Transactional
    public void update(UserDto item) {
        User user = repo.getReferenceById(item.getId());
        user.setFullName(item.getFullName());
        user.setUsername(item.getUsername());
        user.setPassword(passwordEncoder.encode(item.getPassword()));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public User getOneByUsername(String username) {
        return repo.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        if (user == null) {
            log.error("User {} not found in the database", username);
            throw new UsernameNotFoundException("User " + username + " not found in the database");
        } else {
            log.info("User {} found in the database", username);
        }

        Collection<SimpleGrantedAuthority> authorities
                = new ArrayList<>();

        if (user.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );

    }
}
