package uz.bprodevelopment.logisticsapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.service.RoleService;
import uz.bprodevelopment.logisticsapp.base.service.UserService;
import uz.bprodevelopment.logisticsapp.dto.CurrencyDto;
import uz.bprodevelopment.logisticsapp.dto.CurrencyTypeDto;
import uz.bprodevelopment.logisticsapp.entity.Currency;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;
import uz.bprodevelopment.logisticsapp.repo.CurrencyTypeRepo;
import uz.bprodevelopment.logisticsapp.service.CurrencyService;
import uz.bprodevelopment.logisticsapp.service.CurrencyTypeService;

import static uz.bprodevelopment.logisticsapp.base.config.Constants.*;

@SpringBootApplication
public class LionPrintApplication {

    public static void main(String[] args) {
        SpringApplication.run(LionPrintApplication.class, args);
    }


    @Bean
    CommandLineRunner run(
            UserService userService,
            RoleService roleService,
            CurrencyTypeRepo currencyTypeRepo
    ) {
        return args -> {

            Role roleAdmin = roleService.getRole(ROLE_ADMIN);
            if (roleAdmin == null) {
                roleAdmin = roleService.saveRole(new Role(null, ROLE_ADMIN));
            }

            if (userService.getOneByUsername("admin") == null) {
                userService.save(
                        new UserDto(
                                null,
                                "Aliyev valijon",
                                "admin",
                                null,
                                "123",
                                null,
                                false,
                                roleAdmin.getId(),
                                null,

                                null,
                                null,
                                null,
                                null
                        ));
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

            CurrencyType currencyType = currencyTypeRepo.findBySymbol("UZS");
            if (currencyType == null) {
                currencyType = new CurrencyType();
                currencyType.setNameUz("so'm");
                currencyType.setNameRu("сум");
                currencyType.setSymbol("UZS");
                currencyTypeRepo.save(currencyType);
            }


        };
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

}
