package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.entity.Supplier;
import uz.bprodevelopment.logisticsapp.service.SupplierService;
import uz.bprodevelopment.logisticsapp.service.SupplierService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.SUPPLIER_URL;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.SUPPLIER_URL;


@RestController
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService service;

    @GetMapping(SUPPLIER_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        SupplierDto company = service.getOne(id);
        return ResponseEntity.ok().body(company);
    }


    @GetMapping(SUPPLIER_URL)
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

    @GetMapping(SUPPLIER_URL + "/all")
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


    @PostMapping(SUPPLIER_URL)
    public ResponseEntity<?> save(
            @RequestBody SupplierDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping(SUPPLIER_URL)
    public ResponseEntity<?> update(
            @RequestBody SupplierDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(SUPPLIER_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(SUPPLIER_URL + "/add-user")
    public ResponseEntity<?> addUser(
            @RequestBody UserDto item
    ) {
        service.addUser(item);
        return ResponseEntity.ok().build();
    }


}

