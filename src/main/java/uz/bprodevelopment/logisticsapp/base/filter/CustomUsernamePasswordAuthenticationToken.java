package uz.bprodevelopment.logisticsapp.base.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final Long userId;
    private final String username;

    public CustomUsernamePasswordAuthenticationToken(Object username, Object password, Object userId) {
        super(username, password);
        this.userId = (Long) userId;
        this.username = (String) username;
    }

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.username = (String) principal;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
