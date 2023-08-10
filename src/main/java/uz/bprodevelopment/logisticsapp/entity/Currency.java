package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.dto.CurrencyDto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "currencies")
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Double currencyValueInUzs;

    @ManyToOne
    private CurrencyType currencyType;

    @ManyToOne
    private Company company;
    public CurrencyDto toDto(){

        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(id);
        currencyDto.setNameUz(currencyType.getNameUz());
        currencyDto.setNameRu(currencyType.getNameRu());
        currencyDto.setSymbol(currencyType.getSymbol());
        currencyDto.setCurrencyValueInUzs(currencyValueInUzs);

        currencyDto.setCurrencyTypeId(currencyType.getId());
        currencyDto.setCompanyId(company.getId());
        currencyDto.setCompanyName(company.getName());

        currencyDto.setModifiedDate(getModifiedDate());

        return currencyDto;
    }


}