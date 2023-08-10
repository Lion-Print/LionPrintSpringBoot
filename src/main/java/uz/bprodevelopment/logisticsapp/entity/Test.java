package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.dto.CategoryDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "test")
@NoArgsConstructor
@AllArgsConstructor
public class Test extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nameUz;
    private String nameRu;
    public Test(Long id) {
        this.id = id;
    }

    public CategoryDto toDto(){
        return new CategoryDto(id, nameUz, nameRu);
    }

    public Test(CategoryDto categoryDto){
        this.id = categoryDto.getId();
        this.nameUz = categoryDto.getNameUz();
        this.nameRu = categoryDto.getNameRu();
    }

    public Test(Long id, String nameUz, String nameRu) {
        this.id = id;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
    }

    public Test(String nameUz, String nameRu) {
        this.nameUz = nameUz;
        this.nameRu = nameRu;
    }

    public Test(String nameUz) {
        this.nameUz = nameUz;
    }

}
