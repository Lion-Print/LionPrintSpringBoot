package uz.bprodevelopment.logisticsapp.base.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorResponse;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.Success;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.filter.CustomUsernamePasswordAuthenticationToken;
import uz.bprodevelopment.logisticsapp.base.service.UserService;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uz.bprodevelopment.logisticsapp.base.config.Constants.SECURE;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.REFRESH_TOKEN_URL;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.USER_URL;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final MessageSource messageSource;

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
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 10))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName)
                                .collect(Collectors.toList())).sign(algorithm);

                String refreshToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
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

