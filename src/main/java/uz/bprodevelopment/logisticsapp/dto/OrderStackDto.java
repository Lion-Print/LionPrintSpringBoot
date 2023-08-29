package uz.bprodevelopment.logisticsapp.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStackDto {


    private Long id;
    private String createdBy;
    private String phone;
    private Timestamp createdDate;
    private Integer orderCount;

    private List<OrderDto> orders = new ArrayList<>();
    public OrderStack toEntity(){

        OrderStack order = new OrderStack();
        order.setId(id);
        return order;

    }

}
