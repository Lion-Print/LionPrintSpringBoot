package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {


    private Long id;
    private String name;
    private Double price;
    private Integer hasDelivery = 0;
    private Integer hasNds = 0;

    private Long categoryId;
    private Long companyId;
    private Long supplierId;

    private List<ProductDetailDto> productDetails = new ArrayList<>();

    public Product toEntity(){

        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setPrice(this.price);
        product.setHasDelivery(this.hasDelivery);
        product.setHasNds(this.hasNds);

        product.setCategory(new Category(categoryId));
        product.setCompany(new Company(companyId));
        product.setSupplier(new Supplier(supplierId));

        return product;
    }
}
