package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.ProductDetailDto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "product_details")
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private CategoryDetail categoryDetail;

    private String value;

    public ProductDetailDto toDto(){

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(id);
        productDetailDto.setCategoryDetailId(categoryDetail.getId());
        productDetailDto.setCategoryDetailName(BaseAppUtils.getCurrentLanguage().equals("uz") ? categoryDetail.getNameUz() : categoryDetail.getNameRu());
        productDetailDto.setProductId(product.getId());
        productDetailDto.setValue(value);

        return productDetailDto;
    }


}
