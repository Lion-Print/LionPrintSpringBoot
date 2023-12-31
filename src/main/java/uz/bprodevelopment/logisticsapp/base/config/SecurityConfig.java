package uz.bprodevelopment.logisticsapp.base.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.bprodevelopment.logisticsapp.base.filter.CustomAuthenticationFilter;
import uz.bprodevelopment.logisticsapp.base.filter.CustomAuthorizationFilter;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;

import java.util.Arrays;
import java.util.Collections;

import static uz.bprodevelopment.logisticsapp.base.config.Constants.*;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.*;
import static uz.bprodevelopment.logisticsapp.utils.Urls.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final MessageSource messageSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter authenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(), userRepo, passwordEncoder, messageSource);

        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);

        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.authorizeRequests().antMatchers(
                LOGIN_URL,
                FILES_URL + "/**"
        ).permitAll();

        http.authorizeRequests()
                .antMatchers(CURRENCY_TYPE_URL, COMPANY_URL)
                .hasAnyAuthority(ROLE_ADMIN);
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, USER_URL)
                .hasAnyAuthority(ROLE_ADMIN);
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, USER_URL)
                .hasAnyAuthority(ROLE_ADMIN);
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, USER_URL)
                .hasAnyAuthority(ROLE_ADMIN);


        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, CATEGORY_URL, CATEGORY_DETAIL_URL, SUPPLIER_URL,
                        COMPANY_URL + "add-user", COMPANY_URL + "delete-user/**", COMPANY_URL + "block-user/**")
                .hasAnyAuthority(ROLE_COMPANY_ADMIN, ROLE_COMPANY_MANAGER);

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, CATEGORY_URL, CATEGORY_DETAIL_URL, SUPPLIER_URL)
                .hasAnyAuthority(ROLE_COMPANY_ADMIN, ROLE_COMPANY_MANAGER);

        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, CATEGORY_URL + "/**", CATEGORY_DETAIL_URL + "/**")
                .hasAnyAuthority(ROLE_COMPANY_ADMIN, ROLE_COMPANY_MANAGER);

        http.authorizeRequests()
                .antMatchers(SUPPLIER_URL, CATEGORY_URL, CATEGORY_DETAIL_URL)
                .hasAnyAuthority(ROLE_COMPANY_ADMIN, ROLE_COMPANY_MANAGER, ROLE_SUPPLIER_ADMIN);

        http.authorizeRequests()
                .antMatchers(CURRENCY_URL)
                .hasAnyAuthority(ROLE_SUPPLIER_ADMIN);

        http.authorizeRequests()
                .antMatchers(SUPPLIER_URL + "/add-user", SUPPLIER_URL + "/delete-user/**", SUPPLIER_URL + "/block-user/**")
                .hasAnyAuthority(ROLE_SUPPLIER_ADMIN);

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(userRepo), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
