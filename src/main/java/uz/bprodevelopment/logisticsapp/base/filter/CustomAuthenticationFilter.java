package uz.bprodevelopment.logisticsapp.base.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorResponse;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uz.bprodevelopment.logisticsapp.base.config.Constants.SECURE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      UserRepo userRepo,
                                      PasswordEncoder passwordEncoder,
                                      MessageSource messageSource) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User dbUser = userRepo.findByUsername(username);
        if (dbUser.getIsBlocked()) {
            response(request, response, 410, "Foydanaluvchi bloklangan", "Foydanaluvchi bloklangan");
        }
        if (dbUser.getCompany() != null && dbUser.getCompany().getIsBlocked()) {
            response(request, response, 410, "Foydanaluvchi bloklangan", "Foydanaluvchi bloklangan");
        }
        if (dbUser.getSupplier() != null && dbUser.getSupplier().getIsBlocked()) {
            response(request, response, 410, "Foydanaluvchi bloklangan", "Foydanaluvchi bloklangan");
        }

        User optionalUser = userRepo.findByUsername(username);

        if (optionalUser == null) {
            log.info("Error logging in: username is not exist");
            response.setStatus(409);
            response.setContentType(APPLICATION_JSON_VALUE);
            response(request, response, 410, "login xato kiritildi", "login xato kiritildi");
        } else {
            if (!passwordEncoder.matches(password, optionalUser.getPassword())) {
                response.setStatus(410);
                response.setContentType(APPLICATION_JSON_VALUE);
                response(request, response, 410, "Parol xato kiritildi", "Parol xato kiritildi");
            }
        }

        String locale = request.getHeader("Accept-Language");
        if (locale == null || locale.isEmpty()) locale = "uz";

        assert optionalUser != null;
        CustomUsernamePasswordAuthenticationToken authenticationToken
                = new CustomUsernamePasswordAuthenticationToken(username, password, optionalUser.getId(), locale);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication) throws IOException {

        org.springframework.security.core.userdetails.User user
                = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256(SECURE.getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 10))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        uz.bprodevelopment.logisticsapp.base.entity.User dbUser = userRepo.findByUsername(user.getUsername());

        response.setContentType(APPLICATION_JSON_VALUE);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user", dbUser.toDto());
        userMap.put("accessToken", accessToken);
        userMap.put("refreshToken", refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), userMap);
    }

    private void response(HttpServletRequest request, HttpServletResponse response, Integer status, String error, String message) {
        try {
            new ObjectMapper()
                    .writeValue(response.getOutputStream(),
                            ErrorResponse.getInstance().buildMap(
                                    status,
                                    error,
                                    message,
                                    request.getServletPath()
                            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
