package uz.bprodevelopment.logisticsapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import uz.bprodevelopment.logisticsapp.dto.RegionCreationDto;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.*;
import uz.bprodevelopment.logisticsapp.service.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static uz.bprodevelopment.logisticsapp.base.config.Constants.*;

@SpringBootApplication
public class LogisticsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsAppApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    //@Bean
    CommandLineRunner run(
            UserService userService,
            RoleService roleService,
            UserTypeService userTypeService,
            CargoStatusService cargoStatusService,
            PaymentTypeService paymentTypeService,
            TruckTypeService truckTypeService,
            CurrencyTypeService currencyTypeService,
            HalfCargoTypeService halfCargoTypeService,
            RegionService regionService,
            AdminPortionService adminPortionService
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
                                "Muzaffar Usarkulov",
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
                                "Muzaffar Usarkulov(Manager)",
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


            // Cargo statuses
            // id = 1
            if (cargoStatusService.getOneByNameUz("Yangi") == null) {
                cargoStatusService.save(
                        new CargoStatus(
                                null,
                                "Yangi",
                                "Новый",
                                "Янги"
                        )
                );
            }

            // id = 2
            if (cargoStatusService.getOneByNameUz("Jarayonda") == null) {
                cargoStatusService.save(
                        new CargoStatus(
                                null,
                                "Jarayonda",
                                "В процессе",
                                "Жараёнда"
                        )
                );
            }

            // id = 3
            if (cargoStatusService.getOneByNameUz("Yetkazilgan") == null) {
                cargoStatusService.save(
                        new CargoStatus(
                                null,
                                "Yetkazilgan",
                                "Доставлено",
                                "Етказилган"
                        )
                );
            }

            // id = 4
            if (cargoStatusService.getOneByNameUz("Bekor qilingan") == null) {
                cargoStatusService.save(
                        new CargoStatus(
                                null,
                                "Bekor qilingan",
                                "Отменено",
                                "Бекор қилинган"
                        )
                );
            }


            // Payment types
            // id = 1
            if (paymentTypeService.getOneByNameUz("Naqd") == null) {
                paymentTypeService.save(
                        new PaymentType(
                                null,
                                "Naqd",
                                "Наличные",
                                "Нақд"
                        )
                );
            }

            // id = 2
            if (paymentTypeService.getOneByNameUz("Perechisleniya") == null) {
                paymentTypeService.save(
                        new PaymentType(
                                null,
                                "Perechisleniya",
                                "Перечисления",
                                "Перечисления"
                        )
                );
            }


            // Truck types
            // id = 1
            if (truckTypeService.getOneByNameUz("Tent Standart") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Tent Standart",
                                "Тент Стандарт",
                                "Тент Стандарт",
                                0
                        )
                );
            }

            // id = 2
            if (truckTypeService.getOneByNameUz("Tent") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Tent",
                                "Тент",
                                "Тент",
                                1
                        )
                );
            }

            // id = 3
            if (truckTypeService.getOneByNameUz("Tent Mega") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Tent Mega",
                                "Тент Мега",
                                "Тент Мега",
                                10
                        )
                );
            }


            // id = 4
            if (truckTypeService.getOneByNameUz("Tent Paravoz") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Tent Paravoz",
                                "Тент Паровоз",
                                "Тент Паровоз",
                                10
                        )
                );
            }


            // id = 5
            if (truckTypeService.getOneByNameUz("Tent yoki Ref") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Tent yoki Ref",
                                "Тент или Реф",
                                "Тент ёки Реф",
                                20
                        )
                );
            }

            // id = 6
            if (truckTypeService.getOneByNameUz("Ref (Rejimsiz)") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Ref (Rejimsiz)",
                                "Реф (Режимсиз)",
                                "Реф (Без режим)",
                                20
                        )
                );
            }

            // id = 7
            if (truckTypeService.getOneByNameUz("Ref") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Ref",
                                "Реф",
                                "Реф",
                                20
                        )
                );
            }

            // id = 8
            if (truckTypeService.getOneByNameUz("Ref yoki Izoterma") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Ref yoki Izoterma",
                                "Реф или Изотерма",
                                "Реф ёки Изотерма",
                                20
                        )
                );
            }

            // id = 9
            if (truckTypeService.getOneByNameUz("Izoterma") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Izoterma",
                                "Изотерма",
                                "Изотерма",
                                30
                        )
                );
            }

            // id = 10
            if (truckTypeService.getOneByNameUz("Konteyneravoz") == null) {
                truckTypeService.save(
                        new TruckType(
                                null,
                                "Konteyneravoz",
                                "Контейнеравоз",
                                "Контейнеравоз",
                                50
                        )
                );
            }


            // Currency types
            // id = 1
            if (currencyTypeService.getOneByNameUz("so'm") == null) {
                currencyTypeService.save(
                        new CurrencyType(
                                null,
                                "so'm",
                                "сум",
                                "сўм"
                        )
                );
            }

            //  = 2
            if (currencyTypeService.getOneByNameUz("$") == null) {
                currencyTypeService.save(
                        new CurrencyType(
                                null,
                                "$",
                                "$",
                                "$"
                        )
                );
            }

            //  = 3
            if (currencyTypeService.getOneByNameUz("yevro") == null) {
                currencyTypeService.save(
                        new CurrencyType(
                                null,
                                "yevro",
                                "евро",
                                "евро"
                        )
                );
            }


            // AdminPortion
            // = 1
            if (adminPortionService.getOneByCurrencyTypeId(1L) == null) {
                adminPortionService.save(
                        new AdminPortion(
                                null,
                                500000,
                                1L
                        )
                );
            }
            // = 2
            if (adminPortionService.getOneByCurrencyTypeId(2L) == null) {
                adminPortionService.save(
                        new AdminPortion(
                                null,
                                50,
                                2L
                        )
                );
            }
            // = 3
            if (adminPortionService.getOneByCurrencyTypeId(3L) == null) {
                adminPortionService.save(
                        new AdminPortion(
                                null,
                                50,
                                3L
                        )
                );
            }


            // Half Cargo types
            // id = 1
            if (halfCargoTypeService.getOneByNameUz("Dogruz") == null) {
                halfCargoTypeService.save(
                        new HalfCargoType(
                                null,
                                "Dogruz",
                                "Догруз",
                                "Догруз"
                        )
                );
            }

            // id = 2
            if (halfCargoTypeService.getOneByNameUz("Konteyner") == null) {
                halfCargoTypeService.save(
                        new HalfCargoType(
                                null,
                                "Konteyner",
                                "Контейнер",
                                "Контейнер"
                        )
                );
            }

            // id = 3
            if (halfCargoTypeService.getOneByNameUz("Kichiroq mashina kerak") == null) {
                halfCargoTypeService.save(
                        new HalfCargoType(
                                null,
                                "Kichiroq mashina kerak",
                                "Кичироқ машина керак",
                                "Нужна машина поменьше"
                        )
                );
            }

            List<Region> russia = regionService.getByNameUz("Rossiya");
            if (russia == null || russia.isEmpty()) {

                RegionCreationDto rossiya = new RegionCreationDto();
                rossiya.setNameUz("Rossiya");
                rossiya.setNameRu("Россия");
                rossiya.setNameBg("Россия");
                rossiya.setIsTop((byte) 1);
                rossiya.setIsCountry((byte) 1);
                regionService.save(rossiya);

                ObjectMapper mapper = new ObjectMapper();
                List<City> list = mapper.readValue(getRussianJson(), new TypeReference<List<City>>() {
                });
                Set<String> regions = new HashSet<>();
                for (City city : list) {
                    regions.add(city.getSubject());
                }
                for (String region : regions) {
                    boolean find = false;
                    RegionCreationDto regionCreationDto = new RegionCreationDto();
                    for (City city : list) {
                        if (region.equals(city.getSubject())) {
                            find = true;
                            regionCreationDto.setNameUz(region);
                            regionCreationDto.setNameRu(region);
                            regionCreationDto.setNameBg(region);
                            regionCreationDto.setLatitude(city.getCoords().getLat());
                            regionCreationDto.setLongitude(city.getCoords().getLon());
                            regionCreationDto.setIsTop((byte) 1);
                            regionCreationDto.setIsRegion((byte) 1);
                            regionCreationDto.setRegionId(1L);
                            break;
                        }
                    }
                    if (find) {
                        regionService.save(regionCreationDto);
                    }
                }

                for (City city : list) {
                    List<Region> region = regionService.getByNameUz(city.getSubject());

                    RegionCreationDto regionCreationDto = new RegionCreationDto();
                    regionCreationDto.setNameUz(city.getName());
                    regionCreationDto.setNameRu(city.getName());
                    regionCreationDto.setNameBg(city.getName());
                    regionCreationDto.setLatitude(city.getCoords().getLat());
                    regionCreationDto.setLongitude(city.getCoords().getLon());
                    regionCreationDto.setIsTop((byte) 1);
                    regionCreationDto.setIsDistrict((byte) 1);

                    regionCreationDto.setRegionId(region.get(0).getId());

                    regionService.save(regionCreationDto);
                }
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
