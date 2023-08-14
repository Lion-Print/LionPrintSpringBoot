package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
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
    private Supplier supplier;

    public Currency(Long id) {
        this.id = id;
    }

    public CurrencyDto toDto(){

        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(id);
        currencyDto.setName(BaseAppUtils.getCurrentLanguage().equals("uz") ? currencyType.getNameUz() : currencyType.getNameRu());
        currencyDto.setSymbol(currencyType.getSymbol());
        currencyDto.setCurrencyValueInUzs(currencyValueInUzs);

        currencyDto.setCurrencyTypeId(currencyType.getId());
        currencyDto.setSupplierId(supplier.getId());
        currencyDto.setSupplierName(supplier.getName());

        String date = getModifiedDate().toString();
        currencyDto.setModifiedDate(date.substring(0, 11));
        currencyDto.setModifiedTime(date.substring(0, 20));

        return currencyDto;
    }


}