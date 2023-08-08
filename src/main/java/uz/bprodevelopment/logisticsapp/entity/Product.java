package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
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

    private String name;

    private Double price;

    private Integer hasDelivery = 0;

    private Integer hasNds = 0;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Supplier supplier;

    public Product(Long id) {
        this.id = id;
    }

    public ProductDto toDto(){

        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setHasDelivery(hasDelivery);
        productDto.setHasNds(hasNds);
        productDto.setCategoryId(category.getId());
        productDto.setCompanyId(company.getId());
        productDto.setSupplierId(supplier.getId());

        return productDto;
    }
}
