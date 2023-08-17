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

    @OneToOne
    private Product product;

    @Column(nullable = false)
    private Double amount;

    @OneToOne
    private Company company;

    @OneToOne
    private Supplier supplier;

    public OrderDto toDto(){

        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);

        orderDto.setAmount(amount);

        orderDto.setProduct(product.toDto());

        orderDto.setSupplierId(supplier.getId());
        orderDto.setSupplierName(supplier.getName());
        orderDto.setSupplierName(supplier.getPhone());

        orderDto.setCompanyId(company.getId());
        orderDto.setCompanyName(company.getName());
        orderDto.setCompanyName(company.getPhone());

        return orderDto;
    }

}
