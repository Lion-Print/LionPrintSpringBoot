package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.CategoryDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nameUz;

    private String nameRu;
    public Category(Long id) {
        this.id = id;
    }

    public CategoryDto toDto(){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(BaseAppUtils.getCurrentLanguage().equals("uz") ? nameUz : nameRu);
        categoryDto.setNameUz(nameUz);
        categoryDto.setNameRu(nameRu);
        return categoryDto;
    }
}
