package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.entity.Currency;
import uz.bprodevelopment.logisticsapp.entity.Product;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private Double price;
    private Boolean hasDelivery = false;
    private Boolean hasNds = false;
    private Long categoryId;
    private String categoryName;
    private Long supplierId;


    private Long currencyId;

    private String currencyName;
    private String currencySymbol;
    private Double currencyValue;
    private String supplierName;
    private String description;

    private List<ProductDetailDto> details = new ArrayList<>();

    public Product toEntity(){

        Product product = new Product();
        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setHasDelivery(hasDelivery);
        product.setHasNds(hasNds);

        product.setCategory(new Category(categoryId));
        product.setSupplier(new Supplier(supplierId));
        product.setCurrency(new Currency(currencyId));

        return product;
    }
}
