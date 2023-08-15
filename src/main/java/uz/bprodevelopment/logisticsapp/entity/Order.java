package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.CategoryDto;
import uz.bprodevelopment.logisticsapp.dto.OrderDto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    private Double amount;


    public OrderDto toDto(){

        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setAmount(amount);
        orderDto.setProduct(product.toDto());

        return orderDto;
    }

}
