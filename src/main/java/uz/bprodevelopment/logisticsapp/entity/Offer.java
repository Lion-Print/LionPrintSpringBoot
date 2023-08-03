package uz.bprodevelopment.logisticsapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.base.entity.User;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer price;
    private Integer currencyTypeId; // 1 - dollar, 2 - sum, 3 - ruble
    private Integer paymentTypeId; // 1 - Naqd, 2 - Perechisleniya

    @Column(nullable = false) private Integer status = 1; // 1 -> New, 2 -> In Process, 3 -> Delivered, 4 -> Received, -1 -> Cancelled

    private String deliveredFileHashId;

    @OneToOne
    private User user;

    @OneToOne
    private User dispatcherUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Cargo cargo;

    @OneToOne
    private User cancelledUser;
    private String cancelledDescription;

    private Integer ratingOfAdminToDispatcherUser;
    private Integer ratingOfOfferUserToCargoUser;
    private Integer ratingOfCargoUserToOfferUser;
    private Integer ratingOfAdminToOfferUser;
    private Integer ratingOfAdminToCargoUser;

}
