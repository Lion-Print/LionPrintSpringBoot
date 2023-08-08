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
    private String nameUz;
    private String nameRu;

    public Category toEntity(){
        return new Category(this.id, this.nameUz, this.nameRu);
    }

}
