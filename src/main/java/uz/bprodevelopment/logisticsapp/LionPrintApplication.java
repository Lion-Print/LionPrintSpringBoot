package uz.bprodevelopment.logisticsapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.UserType;
import uz.bprodevelopment.logisticsapp.base.service.RoleService;
import uz.bprodevelopment.logisticsapp.base.service.UserService;
import uz.bprodevelopment.logisticsapp.base.service.UserTypeService;
import uz.bprodevelopment.logisticsapp.dto.UserDto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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



    //@Bean
    CommandLineRunner run(
            UserService userService,
            RoleService roleService,
            UserTypeService userTypeService
    ) {
        return args -> {

            Role roleAdmin = roleService.getRole(ROLE_ADMIN);
            if (roleAdmin == null) {
                roleAdmin = roleService.saveRole(new Role(null, ROLE_ADMIN));
            }
            UserType userTypeAdmin = userTypeService.getOneByNameUz("Admin");
            if (userTypeAdmin == null) {
                userTypeAdmin = userTypeService.save(
                        new UserType(null,
                                "Admin",
                                "Администратор",
                                "Админ"));
            }
            if (userService.getOneByUsername("+100") == null) {
                userService.save(
                        new UserDto(
                                null,
                                "Aliyev valijon",
                                "+100",
                                "123",
                                roleAdmin.getId(),
                                userTypeAdmin.getId(),
                                null
                        ));
            }


            Role roleManager = roleService.getRole(ROLE_MANAGER);
            if (roleManager == null) {
                roleManager = roleService.saveRole(new Role(null, ROLE_MANAGER));
            }
            UserType userTypeManager = userTypeService.getOneByNameUz("Menejer");
            if (userTypeManager == null) {
                userTypeManager = userTypeService.save(
                        new UserType(null,
                                "Menejer",
                                "Менеджер",
                                "Менежер"));
            }
            if (userService.getOneByUsername("+101") == null) {
                userService.save(
                        new UserDto(
                                null,
                                "Soliyev Alijon(Manager)",
                                "+101",
                                "123",
                                roleManager.getId(),
                                userTypeManager.getId(),
                                null
                        ));
            }


            Role roleUser = roleService.getRole(ROLE_USER);
            if (roleService.getRole(ROLE_USER) == null) {
                roleUser = roleService.saveRole(new Role(null, ROLE_USER));
            }


            // id = 3
            UserType userTypeTrucker = userTypeService.getOneByNameUz("Yuk tashuvchi");
            if (userTypeTrucker == null) {
                userTypeTrucker = userTypeService.save(
                        new UserType(null,
                                "Yuk tashuvchi",
                                "Перевозчик",
                                "Юк ташувчи"));
            }
            if (userService.getOneByUsername("+102") == null) {
                userService.save(
                        new UserDto(
                                null,
                                "Bunyod Xursanaliyev (Trucker)",
                                "+102",
                                "123",
                                roleUser.getId(),
                                userTypeTrucker.getId(),
                                null
                        ));
            }

            // id = 4
            UserType userTypeClient = userTypeService.getOneByNameUz("Yuk beruvchi");
            if (userTypeClient == null) {
                userTypeClient = userTypeService.save(
                        new UserType(null,
                                "Yuk beruvchi",
                                "Грузоотправитель",
                                "Юк берувчи"));
            }
            if (userService.getOneByUsername("+103") == null) {
                userService.save(
                        new UserDto(
                                null,
                                "Bunyod Xursanaliyev (Client)",
                                "+103",
                                "123",
                                roleUser.getId(),
                                userTypeClient.getId(),
                                null
                        ));
            }

        };
    }


    private String getRussianJson() throws IOException {
        Resource resource = new ClassPathResource("russian_regions.txt");
        InputStream inputStream = resource.getInputStream();
        byte[] bData = FileCopyUtils.copyToByteArray(inputStream);
        return new String(bData, StandardCharsets.UTF_8);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("fcm_service.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "YOUR APP NAME");
        return FirebaseMessaging.getInstance(app);
    }


}
