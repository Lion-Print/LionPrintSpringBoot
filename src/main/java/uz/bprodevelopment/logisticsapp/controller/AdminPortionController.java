package uz.bprodevelopment.logisticsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.entity.AdminPortion;
import uz.bprodevelopment.logisticsapp.service.AdminPortionService;

import static uz.bprodevelopment.logisticsapp.utils.Urls.ADMIN_PORTION_URL;

@RestController
@RequiredArgsConstructor
public class AdminPortionController {

    private final AdminPortionService service;

    @GetMapping(ADMIN_PORTION_URL + "/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(service.getListAll());
    }

    @PostMapping(ADMIN_PORTION_URL)
    public ResponseEntity<?> save(@RequestBody AdminPortion adminPortion) {
        AdminPortion newCargo = service.save(adminPortion);
        return ResponseEntity.ok().body(newCargo);
    }

}