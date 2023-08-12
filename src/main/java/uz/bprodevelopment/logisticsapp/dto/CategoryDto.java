package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {


    private Long id;
    private String name;
    private String nameUz;
    private String nameRu;

    public Category toEntity(){

        Category category = new Category();
        category.setId(id);
        category.setNameUz(nameUz);
        category.setNameRu(nameRu);
        return category;

    }

}
