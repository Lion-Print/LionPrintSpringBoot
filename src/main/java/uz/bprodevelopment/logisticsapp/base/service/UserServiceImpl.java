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
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;
import uz.bprodevelopment.logisticsapp.spec.UserSpec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public User getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<User> getListAll(
            String username,
            String fullName,
            Long userTypeId,
            String sort
    ) {
        UserSpec spec1 = new UserSpec(new SearchCriteria("id", ">", 0));
        Specification<User> spec = Specification.where(spec1);

        if (username != null && fullName != null) {
            UserSpec usernameSpec = new UserSpec(new SearchCriteria("username", ":", username));
            UserSpec fullNameSpec = new UserSpec(new SearchCriteria("fullName", ":", fullName));
            spec.and(Specification.where(usernameSpec.or(fullNameSpec)));
        } else if (fullName != null)
            spec = spec.and(new UserSpec(new SearchCriteria("fullName", ":", fullName)));
        else if (username != null)
            spec = spec.and(new UserSpec(new SearchCriteria("username", ":", username)));

        if (userTypeId != null)
            spec = spec.and(new UserSpec(new SearchCriteria("userTypeId", "=", userTypeId)));

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<User> getList(
            Integer page,
            Integer size,
            String username,
            String fullName,
            Long userTypeId,
            String sort
    ) {
        UserSpec spec1 = new UserSpec(new SearchCriteria("id", ">", 0));
        Specification<User> spec = Specification.where(spec1);

        if (username != null && fullName != null) {
            UserSpec usernameSpec = new UserSpec(new SearchCriteria("username", ":", username));
            UserSpec fullNameSpec = new UserSpec(new SearchCriteria("fullName", ":", fullName));
            spec = spec.and(Specification.where(usernameSpec.or(fullNameSpec)));
        } else if (fullName != null)
            spec = spec.and(new UserSpec(new SearchCriteria("fullName", ":", fullName)));
        else if (username != null)
            spec = spec.and(new UserSpec(new SearchCriteria("username", ":", username)));

        if (userTypeId != null)
            spec = spec.and(new UserSpec(new SearchCriteria("userTypeId", "=", userTypeId)));


        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
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
