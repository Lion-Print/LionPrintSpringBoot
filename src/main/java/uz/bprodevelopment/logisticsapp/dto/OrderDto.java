package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {


    private Long id;
    private Long productId;
    private Long companyId;
    private String companyName;
    private String companyPhone;
    private Long supplierId;
    private String supplierName;
    private String supplierPhone;
    private Double amount;

    private ProductDto product;

    public Order toEntity(){

        Order order = new Order();
        order.setId(id);
        order.setProduct(new Product(productId));
        order.setCompany(new Company(companyId));
        order.setSupplier(new Supplier(supplierId));
        order.setAmount(amount);
        return order;
    }

}
