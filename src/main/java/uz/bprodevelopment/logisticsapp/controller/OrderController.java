package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.dto.OrderDto;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.service.OrderService;

import java.util.List;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.ORDER_URL;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping(ORDER_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        OrderDto order = service.getOne(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping(ORDER_URL)
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


    @PostMapping(ORDER_URL)
    public ResponseEntity<?> save(
            @RequestBody List<OrderDto> item
    ) {
        service.save(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PutMapping(ORDER_URL)
    public ResponseEntity<?> update(
            @RequestBody OrderDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @DeleteMapping(ORDER_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PostMapping(ORDER_URL + "/{id}")
    public ResponseEntity<?> changeStatus(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "status") Integer status
    ) {
        service.changeStatus(id, status);
        return ResponseEntity.ok().body(Success.getInstance());
    }

}

