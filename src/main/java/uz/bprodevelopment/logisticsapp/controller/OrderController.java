package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.dto.OrderDto;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.service.OrderService;

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
            @RequestParam(name = "productId", required = false) Long productId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, productId, sort
                )
        );
    }

    @GetMapping(ORDER_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "productId", required = false) Long productId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getListAll(
                        productId, sort
                )
        );
    }


    @PostMapping(ORDER_URL)
    public ResponseEntity<?> save(
            @RequestBody OrderDto item
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

}

