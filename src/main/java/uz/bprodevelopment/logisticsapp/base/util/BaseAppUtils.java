package uz.bprodevelopment.logisticsapp.base.util;


import org.springframework.security.core.context.SecurityContextHolder;
import uz.bprodevelopment.logisticsapp.base.filter.CustomUsernamePasswordAuthenticationToken;

public final class BaseAppUtils {

    public static String getCurrentUsername(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getCurrentLanguage(){
        return ((CustomUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getLocale();
    }

    public static Long getUserId(){
        return ((CustomUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getUserId();
    }

}
