package uz.bprodevelopment.logisticsapp.base.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.bprodevelopment.logisticsapp.base.filter.CustomUsernamePasswordAuthenticationToken;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @SuppressWarnings("NullableProblems")
    @Override
    public Optional<Long> getCurrentAuditor() {
        CustomUsernamePasswordAuthenticationToken authentication = null;
        if (!( SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            authentication = (CustomUsernamePasswordAuthenticationToken)
                    SecurityContextHolder.getContext().getAuthentication();
        }
        Long userId = null;
        if(authentication != null) {
            userId = authentication.getUserId();
        }
        return Optional.ofNullable(userId);
    }
}
