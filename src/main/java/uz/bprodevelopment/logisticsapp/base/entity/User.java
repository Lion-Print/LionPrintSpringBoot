package uz.bprodevelopment.logisticsapp.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String username;

    private String password;

    private String fcmToken;

    @OneToOne(fetch = FetchType.EAGER)
    private Role role;

    @OneToOne
    private UserType userType;
    private Long carrierTypeId; // 1 - Driver, 2 - Truck Owner, 3 - Dispatcher
    private Integer instructed = 0;

    private Integer debt = 0;
    private Integer cancelledOfferCnt = 0;

    private Double rating;
    private Integer ratingCount;

    private Double latitude;
    private Double longitude;

    private String telegramUsername;
    private String lang = "uz";

}
