package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.dto.OrderStackDto;
import uz.bprodevelopment.logisticsapp.service.OrderStackService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.ORDER_STACK_URL;


@RestController
@RequiredArgsConstructor
public class OrderStackController {

    private final OrderStackService service;

    @GetMapping(ORDER_STACK_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        OrderStackDto order = service.getOne(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping(ORDER_STACK_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, fullName, sort
                )
        );
    }

    @DeleteMapping(ORDER_STACK_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }
}

