package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.CategoryDetailDto;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "category_details")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetail extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameUz;

    @Column(nullable = false)
    private String nameRu;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    public CategoryDetail(Long id) {
        this.id = id;
    }

    public CategoryDetailDto toDto(){

        CategoryDetailDto categoryDetailDto = new CategoryDetailDto();

        categoryDetailDto.setId(id);
        categoryDetailDto.setName(BaseAppUtils.getCurrentLanguage().equals("uz") ? nameUz : nameRu);
        categoryDetailDto.setCategoryId(category.getId());

        return categoryDetailDto;
    }
}
