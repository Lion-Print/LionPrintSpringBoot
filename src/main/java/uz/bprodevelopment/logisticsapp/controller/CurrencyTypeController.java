package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.dto.CurrencyTypeDto;
import uz.bprodevelopment.logisticsapp.service.CurrencyTypeService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.CURRENCY_TYPE_URL;


@RestController
@RequiredArgsConstructor
public class CurrencyTypeController {

    private final CurrencyTypeService service;

    @GetMapping(CURRENCY_TYPE_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        CurrencyTypeDto category = service.getOne(id);
        return ResponseEntity.ok().body(category);
    }


    @GetMapping(CURRENCY_TYPE_URL)
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

    @GetMapping(CURRENCY_TYPE_URL + "/all")
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


    @PostMapping(CURRENCY_TYPE_URL)
    public ResponseEntity<?> save(
            @RequestBody CurrencyTypeDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PutMapping(CURRENCY_TYPE_URL)
    public ResponseEntity<?> update(
            @RequestBody CurrencyTypeDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @DeleteMapping(CURRENCY_TYPE_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }

}

