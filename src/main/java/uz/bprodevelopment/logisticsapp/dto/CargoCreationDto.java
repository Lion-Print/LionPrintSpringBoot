package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Cargo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoCreationDto {

    private Long id;
    private String name;
    private String loadingDate;
    private String fromAddressUz;
    private String fromAddressRu;
    private String fromAddressBg;
    private String toAddressUz;
    private String toAddressRu;
    private String toAddressBg;
    private String customsClAddressUz;
    private String customsClAddressRu;
    private String customsClAddressBg;
    private String amount;
    private Integer measureTypeId = 1; // 1 - tonne, 2 - kg
    private Integer cargoStatusId = 1; // 1-New, 2-In Process, 3-Delivered, 4 - Cancelled
    private Integer truckCount;
    private String truckVolume;
    private String truckDegree;
    private Byte isAutoDegree;
    private Integer truckTypeId; // 1-Tent Standart, 2-Tent, 3-Tent Mega, 4-Tent Paravoz, 5-Tent yoki Ref, 6-Ref(Rejimsiz), 7-Ref, 8-Ref yoki Izoterma, 9-Izoterma, 10-Konteyneravoz
    private Integer truckBoardTypeId; // 1-Usilinniy, 2-Fanera
    private Integer isHalfCargo; // 1-true, null-false, 0-false
    private String halfCargoTypeIds; //e.g: [1, 2] 1-Dogruz, 2-Container, 3-Need small car
    private Integer cashPrice;
    private Integer cashCurrencyTypeId; // 1 - dollar, 2 - sum, 3 - ruble
    private Integer perechisleniyaPrice;
    private Integer perechisleniyaCurrencyTypeId; // 1 - dollar, 2 - sum, 3 - ruble
    private Integer isPriceOfferAble;  // 1-true, null-false, 0-false
    private Integer isAndOr; // 0-or, 1-and
    private String  adr;
    private String cargoVolume;
    private Long userId;

    private String cargoDescription;
    private String truckDescription;
    private String addressDescription;
    private String paymentDescription;

    private Double latitudeFrom;
    private Double longitudeFrom;
    private Double latitudeTo;
    private Double longitudeTo;

    public Cargo toEntity(){

        Cargo cargo = new Cargo();

        cargo.setId(this.id);
        cargo.setName(this.name);
        cargo.setLoadingDate(this.loadingDate);
        cargo.setFromAddressUz(this.fromAddressUz);
        cargo.setFromAddressRu(this.fromAddressRu);
        cargo.setFromAddressBg(this.fromAddressBg);
        cargo.setToAddressUz(this.toAddressUz);
        cargo.setToAddressRu(this.toAddressRu);
        cargo.setToAddressBg(this.toAddressBg);
        cargo.setCustomsClAddressUz(this.customsClAddressUz);
        cargo.setCustomsClAddressRu(this.customsClAddressRu);
        cargo.setCustomsClAddressBg(this.customsClAddressBg);
        cargo.setAmount(this.amount);
        cargo.setMeasureTypeId(this.measureTypeId);
        cargo.setTruckCount(this.truckCount);
        cargo.setTruckVolume(this.truckVolume);
        cargo.setTruckDegree(this.truckDegree);
        cargo.setIsAutoDegree(this.isAutoDegree);
        cargo.setTruckTypeId(this.truckTypeId);
        cargo.setTruckBoardTypeId(this.truckBoardTypeId);
        cargo.setIsHalfCargo(this.isHalfCargo);
        cargo.setHalfCargoTypeIds(this.halfCargoTypeIds);
        cargo.setCashPrice(this.cashPrice);
        cargo.setCashCurrencyTypeId(this.cashCurrencyTypeId);
        cargo.setPerechisleniyaPrice(this.perechisleniyaPrice);
        cargo.setPerechisleniyaCurrencyTypeId(this.perechisleniyaCurrencyTypeId);
        cargo.setIsPriceOfferAble(this.isPriceOfferAble);
        cargo.setIsAndOr(this.isAndOr);
        cargo.setAdr(this.adr);
        cargo.setCargoVolume(this.cargoVolume);

        cargo.setCargoDescription(this.cargoDescription);
        cargo.setTruckDescription(this.truckDescription);
        cargo.setAddressDescription(this.addressDescription);
        cargo.setPaymentDescription(this.paymentDescription);

        cargo.setLatitudeFrom(this.latitudeFrom);
        cargo.setLongitudeFrom(this.longitudeFrom);
        cargo.setLatitudeTo(this.latitudeTo);
        cargo.setLongitudeTo(this.longitudeTo);

        User user = new User();
        user.setId(this.userId);

        cargo.setUser(user);

        return cargo;

    }

}
