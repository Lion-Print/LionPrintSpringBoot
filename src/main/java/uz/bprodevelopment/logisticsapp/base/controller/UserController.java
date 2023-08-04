package uz.bprodevelopment.logisticsapp.base.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.service.UserService;
import uz.bprodevelopment.logisticsapp.dto.UserDto;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.USER_URL;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(USER_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        User users = service.getOne(id);
        return ResponseEntity.ok().body(users);
    }


    @GetMapping(USER_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getList(
                        page, size, username, fullName, sort
                )
        );
    }

    @GetMapping(USER_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return ResponseEntity.ok().body(
                service.getListAll(
                        username, fullName, sort
                )
        );
    }


    @PostMapping(USER_URL)
    public ResponseEntity<?> save(
            @RequestBody UserDto item
    ) {
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping(USER_URL)
    public ResponseEntity<?> update(
            @RequestBody UserDto item
    ) {
        service.update(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(USER_URL + "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}

