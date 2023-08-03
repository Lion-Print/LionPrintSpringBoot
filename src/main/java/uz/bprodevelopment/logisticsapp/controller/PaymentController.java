package uz.bprodevelopment.logisticsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.dto.PaymentDto;
import uz.bprodevelopment.logisticsapp.entity.Payment;
import uz.bprodevelopment.logisticsapp.service.PaymentService;

import static uz.bprodevelopment.logisticsapp.utils.Urls.PAYMENT_URL;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping(PAYMENT_URL + "/{id}")
    public ResponseEntity<Payment> getOne(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(service.getOne(id));
    }

    @GetMapping(PAYMENT_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "amount", required = false) Integer amount,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {

        return ResponseEntity.ok().body(service.getList(
                page, size, userId, amount, sort
        ));
    }

    @GetMapping(PAYMENT_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "amount", required = false) Integer amount,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {


        return ResponseEntity.ok().body(service.getListAll(
                userId, amount, sort
        ));

    }

    @PostMapping(PAYMENT_URL)
    public ResponseEntity<?> save(@RequestBody PaymentDto item) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(PAYMENT_URL + "/{id}")
    public ResponseEntity<?> save(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}