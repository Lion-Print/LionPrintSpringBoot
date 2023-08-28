package uz.bprodevelopment.logisticsapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false)
    private Double amount;

    private Integer status = 1; // 1-new, 2-received, 3-on the way, 4-delivered

    @OneToOne
    private Company company;

    @OneToOne
    private Supplier supplier;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderStack orderStack;

    public OrderDto toDto(){

        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);

        orderDto.setProductId(product.getId());
        orderDto.setAmount(amount);
        orderDto.setStatus(status);

        orderDto.setCompanyId(company.getId());
        orderDto.setCompanyName(company.getName());
        orderDto.setCompanyPhone(company.getPhone());

        orderDto.setSupplierId(supplier.getId());
        orderDto.setSupplierName(supplier.getName());
        orderDto.setSupplierPhone(supplier.getPhone());
        orderDto.setProductDto(product.toDto());

        return orderDto;
    }

}
