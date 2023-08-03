package uz.bprodevelopment.logisticsapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.entity.User;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cargo extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String loadingDate;

    @Column(nullable = false) private String fromAddressUz;
    @Column(nullable = false) private String fromAddressRu;
    @Column(nullable = false) private String fromAddressBg;
    @Column(nullable = false) private String toAddressUz;
    @Column(nullable = false) private String toAddressRu;
    @Column(nullable = false) private String toAddressBg;
    @Column(name = "customs_cl_address_uz") private String customsClAddressUz;
    @Column(name = "customs_cl_address_ru") private String customsClAddressRu;
    @Column(name = "customs_cl_address_bg") private String customsClAddressBg;

    @Column(nullable = false) private String amount;
    @Column(nullable = false) private Integer measureTypeId = 1; // 1 - tonne, 2 - kg
    @Column(nullable = false) private Integer status = 1; // 1-New, 2-In Process, 3-Delivered

    private Integer truckCount;
    private String truckVolume;
    private String truckDegree;
    private Byte isAutoDegree;
    private Integer truckTypeId; // 1-Tent Standart, 2-Tent, 3-Tent Mega, 4-Tent Paravoz, 5-Tent yoki Ref, 6-Ref(Rejimsiz), 7-Ref, 8-Ref yoki Izoterma, 9-Izoterma, 10-Konteyneravoz
    private Integer truckBoardTypeId; // 1-Usilinniy, 2-Fanera

    private Integer isHalfCargo; // 1-true, null-false, 0-false
    private String halfCargoTypeIds; //e.g: [1, 2]  // 1-Dogruz, 2-Container, 3-Need small car

    private Integer cashPrice;
    private Integer cashCurrencyTypeId; // 1 - dollar, 2 - sum, 3 - ruble

    private Integer perechisleniyaPrice;
    private Integer perechisleniyaCurrencyTypeId; // 1 - dollar, 2 - sum, 3 - ruble

    private Integer isPriceOfferAble;  // 1-true, null-false, 0-false

    private Integer isAndOr;  // 0-or, 1-and  this field for payment

    private String adr;
    private String cargoVolume;

    private String cargoDescription;
    private String truckDescription;
    private String addressDescription;
    private String paymentDescription;

    private Double latitudeFrom;
    private Double longitudeFrom;
    private Double latitudeTo;
    private Double longitudeTo;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "cargo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Offer> offers;

    private Byte isOnTheWay = 0; // 0 -> is cargo is not on the way, 1 -> is cargo on the way
}
