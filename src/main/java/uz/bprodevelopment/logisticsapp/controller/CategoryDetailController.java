package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.dto.CategoryDetailDto;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;
import uz.bprodevelopment.logisticsapp.service.CategoryDetailService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.CATEGORY_DETAIL_URL;


@RestController
@RequiredArgsConstructor
public class CategoryDetailController {

    private final CategoryDetailService service;

    @GetMapping(CATEGORY_DETAIL_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        CategoryDetail company = service.getOne(id);
        return ResponseEntity.ok().body(company);
    }


    @GetMapping(CATEGORY_DETAIL_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, name, categoryId, sort
                )
        );
    }

    @GetMapping(CATEGORY_DETAIL_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getListAll(
                        name, categoryId, sort
                )
        );
    }


    @PostMapping(CATEGORY_DETAIL_URL)
    public ResponseEntity<?> save(
            @RequestBody CategoryDetailDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping(CATEGORY_DETAIL_URL)
    public ResponseEntity<?> update(
            @RequestBody CategoryDetailDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(CATEGORY_DETAIL_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}

