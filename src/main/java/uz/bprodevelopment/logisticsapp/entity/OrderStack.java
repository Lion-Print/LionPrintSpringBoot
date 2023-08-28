package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.dto.OrderStackDto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "order_stacks")
@NoArgsConstructor
@AllArgsConstructor
public class OrderStack extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    public OrderStackDto toDto(){

        OrderStackDto orderStackDto = new OrderStackDto();
        orderStackDto.setId(id);
        orderStackDto.setCreatedBy(getCreatedBy().getFullName());
        orderStackDto.setCreatedDate(getCreatedDate());

        return orderStackDto;
    }

}
