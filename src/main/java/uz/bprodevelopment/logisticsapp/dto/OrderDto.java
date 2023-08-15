package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.entity.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {


    private Long id;
    private Long productId;
    private Double amount;

    private ProductDto product;

    public Order toEntity(){

        Order order = new Order();
        order.setId(id);
        order.setProduct(new Product(productId));
        order.setAmount(amount);

        return order;
    }

}
