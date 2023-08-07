package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.CompanyProduct;
import uz.bprodevelopment.logisticsapp.service.CompanyProductService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.COMPANY_PRODUCT_URL;


@RestController
@RequiredArgsConstructor
public class CompanyProductController {

    private final CompanyProductService service;

    @GetMapping(COMPANY_PRODUCT_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        CompanyProduct company = service.getOne(id);
        return ResponseEntity.ok().body(company);
    }


    @GetMapping(COMPANY_PRODUCT_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, name, sort
                )
        );
    }

    @GetMapping(COMPANY_PRODUCT_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getListAll(
                        name, sort
                )
        );
    }


    @PostMapping(COMPANY_PRODUCT_URL)
    public ResponseEntity<?> save(
            @RequestBody CompanyProduct item
    ) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping(COMPANY_PRODUCT_URL)
    public ResponseEntity<?> update(
            @RequestBody CompanyProduct item
    ) {
        service.update(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(COMPANY_PRODUCT_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}

