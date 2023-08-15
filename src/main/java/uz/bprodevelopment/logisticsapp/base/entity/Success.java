package uz.bprodevelopment.logisticsapp.base.entity;

import lombok.Data;

@Data
public class Success {

    private static Success single_instance = null;
    public String message;
    private Success() {message = "ok";}
    public static synchronized Success getInstance() {
        if (single_instance == null)
            single_instance = new Success();

        return single_instance;
    }

}
