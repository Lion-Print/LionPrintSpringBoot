package uz.bprodevelopment.logisticsapp.controller;


import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.dto.CompanyDto;
import uz.bprodevelopment.logisticsapp.service.CompanyService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.COMPANY_URL;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.COMPANY_URL;


@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @GetMapping(COMPANY_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        CompanyDto company = service.getOne(id);
        return ResponseEntity.ok().body(company);
    }


    @GetMapping(COMPANY_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "director", required = false) String director,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "isBlocked", required = false) Boolean isBlocked,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, name, director, phone, isBlocked, sort
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
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PutMapping(COMPANY_URL)
    public ResponseEntity<?> update(
            @RequestBody CompanyDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @DeleteMapping(COMPANY_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PostMapping(COMPANY_URL + "/add-user")
    public ResponseEntity<?> addUser(
            @RequestBody UserDto item
    ) {
        service.addUser(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PostMapping(COMPANY_URL + "/delete-user/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable(name = "id") Long id
    ) {
        service.deleteUser(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PostMapping(COMPANY_URL + "/block-user/{id}")
    public ResponseEntity<?> blockUser(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "isBlock", defaultValue = "true") Boolean isBlock
    ) {
        service.blockUser(id, isBlock);
        return ResponseEntity.ok().body(Success.getInstance());
    }

}

