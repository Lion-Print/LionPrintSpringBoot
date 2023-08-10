package uz.bprodevelopment.logisticsapp.controller;


import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.dto.ProductDto;
import uz.bprodevelopment.logisticsapp.entity.Product;
import uz.bprodevelopment.logisticsapp.service.ProductService;

import javax.servlet.http.HttpServletRequest;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.PRODUCT_URL;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping(PRODUCT_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        ProductDto item = service.getOne(id);
        return ResponseEntity.ok().body(item);
    }


    @GetMapping(PRODUCT_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "price", required = false) Double price,
            @RequestParam(name = "hasDelivery", required = false) Integer hasDelivery,
            @RequestParam(name = "hasNds", required = false) Integer hasNds,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestParam(name = "supplierId", required = false) Long supplierId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
            @RequestHeader(name = "descending", required = false, defaultValue = "true") Boolean descending
    ) {
        return ResponseEntity.ok().body(
                service.getList(page, size, name, price, hasDelivery, hasNds, categoryId, supplierId, sort, descending)
        );
    }

    @GetMapping(PRODUCT_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "price", required = false) Double price,
            @RequestParam(name = "hasDelivery", required = false) Integer hasDelivery,
            @RequestParam(name = "hasNds", required = false) Integer hasNds,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestParam(name = "supplierId", required = false) Long supplierId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
            @RequestHeader(name = "descending", required = false, defaultValue = "true") Boolean descending
    ) {
        return ResponseEntity.ok().body(service.getListAll(name, price, hasDelivery, hasNds, categoryId, supplierId, sort, descending));
    }


    @PostMapping(PRODUCT_URL)
    public ResponseEntity<?> save(
            @RequestBody ProductDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping(PRODUCT_URL)
    public ResponseEntity<?> update(
            @RequestBody ProductDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(PRODUCT_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}

