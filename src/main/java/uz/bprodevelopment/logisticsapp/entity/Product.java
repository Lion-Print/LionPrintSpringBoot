package uz.bprodevelopment.logisticsapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.ProductDto;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity(name = "product")
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    @Formula("price * (select cur.currency_value_in_uzs from currencies cur where cur.id=currency_id)")
    private Double priceInUzs;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="supplier_id", nullable=false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="currency_id", nullable=false)
    private Currency currency;

    private String description;


    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product")
    private List<ProductDetail> productDetails;

    public Product(Long id) {
        this.id = id;
    }

    public ProductDto toDto(){

        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setPrice(price);
        productDto.setHasDelivery(hasDelivery);
        productDto.setHasNds(hasNds);
        productDto.setIsLowestPrice(isLowestPrice);
        productDto.setMaxAmount(maxAmount);
        productDto.setMinAmount(minAmount);
        productDto.setManufacturer(manufacturer);
        productDto.setFactoryDate(factoryDate);
        productDto.setExpDate(expDate);
        productDto.setCountry(country);
        productDto.setDescription(description);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(BaseAppUtils.getCurrentLanguage().equals("uz") ? category.getNameUz() : category.getNameRu());
        productDto.setSupplierId(supplier.getId());
        productDto.setSupplierName(supplier.getName());
        productDto.setSupplierPhone(supplier.getPhone());
        productDto.setCurrencyId(currency.getId());
        productDto.setCurrencyName(BaseAppUtils.getCurrentLanguage().equals("uz") ? currency.getCurrencyType().getNameUz() : currency.getCurrencyType().getNameRu());
        productDto.setCurrencySymbol(currency.getCurrencyType().getSymbol());
        productDto.setCurrencyValue(currency.getCurrencyValueInUzs());

        return productDto;
    }
}
