package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.dto.CurrencyTypeDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "currency_types")
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyType extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nameUz;
    private String nameRu;

    private String symbol;

    public CurrencyType(Long id) {
        this.id = id;
    }

    public CurrencyTypeDto toDto(){

        CurrencyTypeDto currencyTypeDto = new CurrencyTypeDto();
        currencyTypeDto.setId(id);
        currencyTypeDto.setNameUz(nameUz);
        currencyTypeDto.setNameRu(nameRu);
        currencyTypeDto.setSymbol(symbol);

        return currencyTypeDto;
    }

}