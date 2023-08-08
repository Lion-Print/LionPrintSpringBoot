package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailDto {


    private Long id;
    private String nameUz;
    private String nameRu;
    private Long categoryId;

    public CategoryDetail toEntity(){

        CategoryDetail categoryDetail = new CategoryDetail();
        categoryDetail.setId(this.id);
        categoryDetail.setNameUz(this.nameUz);
        categoryDetail.setNameRu(this.nameRu);
        categoryDetail.setCategory(new Category(this.categoryId));

        return categoryDetail;
    }

}
