package uz.bprodevelopment.logisticsapp.base.util;


import org.springframework.security.core.context.SecurityContextHolder;

public final class BaseAppUtils {

    public static String getCurrentUsername(){

        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
