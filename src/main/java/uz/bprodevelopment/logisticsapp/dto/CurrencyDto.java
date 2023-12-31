package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Currency;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {

    private Long id;
    private String name;
    private String symbol;

    private Double currencyValueInUzs;

    private Long currencyTypeId;
    private Long supplierId;
    private String supplierName;

    private String modifiedDate;
    private String modifiedTime;
    public Currency toEntity() {

        Currency currency = new Currency();
        currency.setId(id);
        currency.setCurrencyValueInUzs(currencyValueInUzs);
        currency.setCurrencyType(new CurrencyType(currencyTypeId));
        currency.setSupplier(new Supplier(supplierId));

        return currency;
    }

}
