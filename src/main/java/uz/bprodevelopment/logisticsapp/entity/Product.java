package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.ProductDto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
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

    @ManyToOne
    private Category category;

    @ManyToOne
    private Supplier supplier;

    private String description;

    public Product(Long id) {
        this.id = id;
    }

    public ProductDto toDto(){

        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setHasDelivery(hasDelivery);
        productDto.setHasNds(hasNds);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(BaseAppUtils.getCurrentLanguage().equals("uz") ? category.getNameUz() : category.getNameRu());
        productDto.setSupplierId(supplier.getId());
        productDto.setSupplierName(supplier.getName());

        return productDto;
    }
}
