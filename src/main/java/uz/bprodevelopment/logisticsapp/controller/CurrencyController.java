package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.dto.CurrencyDto;
import uz.bprodevelopment.logisticsapp.service.CurrencyService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.CURRENCY_URL;


@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService service;

    @GetMapping(CURRENCY_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        CurrencyDto item = service.getOne(id);
        return ResponseEntity.ok().body(item);
    }


    @GetMapping(CURRENCY_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "supplierId", required = false) Long supplierId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, supplierId, sort
                )
        );
    }

    @GetMapping(CURRENCY_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "supplierId", required = false) Long supplierId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getListAll(
                        supplierId, sort
                )
        );
    }


    @PostMapping(CURRENCY_URL)
    public ResponseEntity<?> save(
            @RequestBody CurrencyDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PutMapping(CURRENCY_URL)
    public ResponseEntity<?> update(
            @RequestBody CurrencyDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @DeleteMapping(CURRENCY_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }

}

