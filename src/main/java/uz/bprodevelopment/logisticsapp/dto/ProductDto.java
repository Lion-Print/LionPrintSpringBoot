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
    private Boolean isLowestPrice = false;

    private Double maxAmount;
    private Double minAmount;
    private String manufacturer;
    private String factoryDate;
    private String expDate;
    private String country;
    private String description;

    private Long categoryId;
    private String categoryName;
    private Long supplierId;
    private String supplierName;
    private String supplierPhone;
    private Long currencyId;
    private String currencyName;
    private String currencySymbol;
    private Double currencyValue;


    private List<ProductDetailDto> details = new ArrayList<>();

    public Product toEntity(){

        Product product = new Product();
        product.setId(id);
        product.setPrice(price);
        product.setHasDelivery(hasDelivery);
        product.setHasNds(hasNds);
        product.setIsLowestPrice(isLowestPrice);
        product.setMaxAmount(maxAmount);
        product.setMinAmount(minAmount);
        product.setManufacturer(manufacturer);
        product.setFactoryDate(factoryDate);
        product.setExpDate(expDate);
        product.setCountry(country);
        product.setDescription(description);

        product.setCategory(new Category(categoryId));
        product.setSupplier(new Supplier(supplierId));
        product.setCurrency(new Currency(currencyId));

        return product;
    }
}
