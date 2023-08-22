package uz.bprodevelopment.logisticsapp.base.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorResponse;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uz.bprodevelopment.logisticsapp.base.config.Constants.SECURE;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.*;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(USER_URL + "/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        UserDto user = service.getOne(id);
        return ResponseEntity.ok().body(user);
    }


    @GetMapping(USER_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestParam(name = "supplierId", required = false) Long supplierId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort) {
        return ResponseEntity.ok().body(service.getList(page, size, username, fullName, companyId, supplierId, sort));
    }

    @GetMapping(USER_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestParam(name = "supplierId", required = false) Long supplierId,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort) {
        return ResponseEntity.ok().body(service.getListAll(username, fullName, companyId, supplierId, sort));
    }


    @PostMapping(USER_URL)
    public ResponseEntity<?> save(@RequestBody UserDto item) {
        service.save(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PutMapping(USER_URL)
    public ResponseEntity<?> update(@RequestBody UserDto item) {
        service.update(item);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @DeleteMapping(USER_URL + "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @PostMapping(CHANGE_PASSWORD_URL)
    public ResponseEntity<?> changePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword)  {
        service.changePassword(oldPassword, newPassword);
        return ResponseEntity.ok().body(Success.getInstance());
    }

    @GetMapping(REFRESH_TOKEN_URL)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {

                String token = authorizationHeader.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(SECURE.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();

                User user = service.getOneByUsername(username);

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 30))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName)
                                .collect(Collectors.toList())).sign(algorithm);

                String refreshToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName)
                                .collect(Collectors.toList())).sign(algorithm);

                response.setContentType(APPLICATION_JSON_VALUE);
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("user", user.toDto());
                userMap.put("accessToken", accessToken);
                userMap.put("refreshToken", refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), userMap);

            } catch (Exception exception) {
                response.setStatus(401);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), ErrorResponse.getInstance().buildMap(500, exception.getMessage(), exception.getCause().getMessage(), request.getServletPath()));

            }
        } else {

            response.setStatus(401);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), ErrorResponse.getInstance().buildMap(401, "Authorization does not exist", "Authorization does not exist", request.getServletPath()));

        }
    }

}

