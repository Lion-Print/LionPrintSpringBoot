package uz.bprodevelopment.logisticsapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.service.RoleService;
import uz.bprodevelopment.logisticsapp.base.service.UserService;

import java.util.Locale;

import static uz.bprodevelopment.logisticsapp.base.config.Constants.*;

@SpringBootApplication
public class LionPrintApplication {

    public static void main(String[] args) {
        SpringApplication.run(LionPrintApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


    @Bean
    CommandLineRunner run(
            UserService userService,
            RoleService roleService
    ) {
        return args -> {

            Role roleAdmin = roleService.getRole(ROLE_ADMIN);
            if (roleAdmin == null) {
                roleAdmin = roleService.saveRole(new Role(null, ROLE_ADMIN));
            }
            if (userService.getOneByUsername("+100") == null) {
                userService.save(
                        new UserDto(
                                null,
                                "Aliyev valijon",
                                "+100",
                                "123",
                                null,
                                roleAdmin.getId(),
                                null,
                                null,
                                null
                        ));
            }


            Role roleManager = roleService.getRole(ROLE_MANAGER);
            if (roleManager == null) {
                roleService.saveRole(new Role(null, ROLE_MANAGER));
            }

            Role roleCompanyAdmin = roleService.getRole(ROLE_COMPANY_ADMIN);
            if (roleCompanyAdmin == null) {
                roleService.saveRole(new Role(null, ROLE_COMPANY_ADMIN));
            }

            Role roleCompanyManager = roleService.getRole(ROLE_COMPANY_MANAGER);
            if (roleCompanyManager == null) {
                roleService.saveRole(new Role(null, ROLE_COMPANY_MANAGER));
            }

            Role roleSupplierAdmin = roleService.getRole(ROLE_SUPPLIER_ADMIN);
            if (roleSupplierAdmin == null) {
                roleService.saveRole(new Role(null, ROLE_SUPPLIER_ADMIN));
            }

            Role roleSupplierManager = roleService.getRole(ROLE_SUPPLIER_MANAGER);
            if (roleSupplierManager == null) {
                roleService.saveRole(new Role(null, ROLE_SUPPLIER_MANAGER));
            }

        };
    }

}
