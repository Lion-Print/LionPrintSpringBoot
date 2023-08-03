package uz.bprodevelopment.logisticsapp.base.security;


import lombok.RequiredArgsConstructor;
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
import uz.bprodevelopment.logisticsapp.base.filter.CustomAuthenticationFilter;
import uz.bprodevelopment.logisticsapp.base.filter.CustomAuthorizationFilter;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter authenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(), userRepo, passwordEncoder);

        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(
                LOGIN_URL,
                SIGNUP_URL,
                FILES_URL + "/**"
        ).permitAll();

        http.authorizeRequests().antMatchers(
                USER_URL
        ).hasAnyAuthority(
                ROLE_ADMIN,
                ROLE_MANAGER
        );

        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, REGION_URL + "/**")
                .hasAnyAuthority(ROLE_ADMIN);

        http.authorizeRequests()
                .antMatchers( USER_URL + "/payment/**")
                .hasAnyAuthority(ROLE_ADMIN);

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(userRepo), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
