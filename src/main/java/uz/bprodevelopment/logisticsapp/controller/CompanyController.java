package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.dto.CompanyDto;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.service.CompanyService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.COMPANY_URL;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.USER_URL;


@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @GetMapping(COMPANY_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        Company company = service.getOne(id);
        return ResponseEntity.ok().body(company);
    }


    @GetMapping(COMPANY_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "director", required = false) String director,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, name, director, phone, sort
                )
        );
    }

    @GetMapping(COMPANY_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "director", required = false) String director,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getListAll(
                        name, director, phone, sort
                )
        );
    }


    @PostMapping(COMPANY_URL)
    public ResponseEntity<?> save(
            @RequestBody CompanyDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping(COMPANY_URL)
    public ResponseEntity<?> update(
            @RequestBody CompanyDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(COMPANY_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(COMPANY_URL + "/add-user")
    public ResponseEntity<?> addUser(
            @RequestBody UserDto item
    ) {
        service.addUser(item);
        return ResponseEntity.ok().build();
    }


}

