package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.security.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyTypeDto {

    private Long id;
    private String nameUz;
    private String nameRu;
    private String symbol;
    public CurrencyType toEntity() {

        CurrencyType currency = new CurrencyType();
        currency.setId(id);
        currency.setNameUz(nameUz);
        currency.setNameRu(nameRu);
        currency.setSymbol(symbol);

        return currency;
    }

}
