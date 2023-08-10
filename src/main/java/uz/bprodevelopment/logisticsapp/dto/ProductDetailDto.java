
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
    private Long categoryDetailId;
    private String categoryDetailName;
    private Long productId;
    private String value;



    public ProductDetail toEntity(){

        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(this.id);
        productDetail.setProduct(new Product(productId));
        productDetail.setCategoryDetail(new CategoryDetail(categoryDetailId));
        productDetail.setValue(value);

        return productDetail;
    }

}
