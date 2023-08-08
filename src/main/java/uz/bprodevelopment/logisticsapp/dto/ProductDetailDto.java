
package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    private Long id;
    private Long productId;
    private Long categoryDetailId;
    private String value;

    private String detailNameUz;
    private String detailNameRu;


    public ProductDetail toEntity(){

        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(this.id);
        productDetail.setProduct(new Product(productId));
        productDetail.setCategoryDetail(new CategoryDetail(categoryDetailId));
        productDetail.setValue(value);

        return productDetail;
    }

}
