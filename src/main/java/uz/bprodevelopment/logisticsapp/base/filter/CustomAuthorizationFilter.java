package uz.bprodevelopment.logisticsapp.base.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorResponse;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uz.bprodevelopment.logisticsapp.base.config.Constants.SECURE;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.*;


@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepo usersRepo;

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getServletPath().equals(LOGIN_URL)
                || request.getServletPath().startsWith(FILES_URL)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {

                    String token = authorizationHeader.substring(7);
                    Algorithm algorithm = Algorithm.HMAC256(SECURE.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    User users = usersRepo.findByUsername(username);
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(
                            role -> {
                                authorities.add(new SimpleGrantedAuthority(role));
                            }
                    );

                    String locale = request.getHeader("Accept-Language");
                    if (locale == null || locale.isEmpty()) locale = "uz";

                    CustomUsernamePasswordAuthenticationToken authenticationToken =
                            new CustomUsernamePasswordAuthenticationToken(username, null, authorities, users.getId(), locale);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);

                } catch (Exception exception) {

                    if (exception instanceof TokenExpiredException
                            || exception instanceof SignatureVerificationException
                            || exception instanceof JWTDecodeException) {
                        log.info("Error logging in: {}", exception.toString());
                        response.setStatus(401);
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper()
                                .writeValue(response.getOutputStream(),
                                        ErrorResponse.getInstance().buildMap(
                                                401,
                                                exception.getMessage(),
                                                "Ma'lumotlarni qayta ishlashda xatolik yuz berdi",
                                                request.getServletPath()
                                        )
                                );
                    } else {
                        log.info("Error logging in: {}", exception.toString());
                        response.setStatus(500);
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper()
                                .writeValue(response.getOutputStream(),
                                        ErrorResponse.getInstance().buildMap(
                                                500,
                                                exception.getMessage(),
                                                exception.getCause().getMessage(),
                                                request.getServletPath()
                                        )
                                );
                    }

                }
            } else {

                log.info("Authorization does not exist");
                response.setStatus(401);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper()
                        .writeValue(response.getOutputStream(),
                                ErrorResponse.getInstance().buildMap(
                                        401,
                                        "Authorization does not exist",
                                        "Iltimos qaytadan dasturga username va parolni terib kiring",
                                        request.getServletPath()
                                ));

            }
        }

    }
}
