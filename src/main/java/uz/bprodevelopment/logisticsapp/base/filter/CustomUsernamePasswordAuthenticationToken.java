package uz.bprodevelopment.logisticsapp.base.filter;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final Long userId;
    private final String username;
    private final String locale;

    public CustomUsernamePasswordAuthenticationToken(Object username, Object password, Object userId, String locale) {
        super(username, password);
        this.userId = (Long) userId;
        this.username = (String) username;
        this.locale = locale;
    }

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long userId, String locale) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.username = (String) principal;
        this.locale = locale;
    }

}
