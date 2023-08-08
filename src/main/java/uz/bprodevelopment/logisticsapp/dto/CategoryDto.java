package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {


    private Long id;
    private String nameUz;
    private String nameRu;

    public Category toEntity(){

        Category category = new Category();
        category.setId(this.id);
        category.setNameUz(this.nameUz);
        category.setNameRu(this.nameRu);

        return category;
    }

}
