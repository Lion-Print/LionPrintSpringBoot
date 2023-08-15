package uz.bprodevelopment.logisticsapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.dto.CategoryDto;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.service.CategoryService;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.CATEGORY_URL;


@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping(CATEGORY_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        CategoryDto category = service.getOne(id);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping(CATEGORY_URL)
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

    @GetMapping(CATEGORY_URL + "/all")
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


    @PostMapping(CATEGORY_URL)
    public ResponseEntity<?> save(
            @RequestBody CategoryDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PutMapping(CATEGORY_URL)
    public ResponseEntity<?> update(
            @RequestBody CategoryDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @DeleteMapping(CATEGORY_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }

}

