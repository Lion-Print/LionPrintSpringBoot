package uz.bprodevelopment.logisticsapp.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import javax.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = true)
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

    private String phone;

    private String password;

    private String fcmToken;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    private Company company;

    @OneToOne
    private Supplier supplier;

    private Boolean isBlocked = false;

    public User(Long id) {
        this.id = id;
    }

    public UserDto toDto(){

        UserDto userDto = new UserDto();
        userDto.setId(this.id);
        userDto.setFullName(this.fullName);
        userDto.setUsername(this.username);
        userDto.setPhone(this.phone);
        userDto.setIsBlocked(this.isBlocked);

        if (company != null) {
            userDto.setCompanyId(company.getId());
            userDto.setCompanyName(company.getName());
        }
        if (supplier != null) {
            userDto.setSupplierId(supplier.getId());
            userDto.setSupplierName(supplier.getName());
        }

        userDto.setRoles(roles);

        return userDto;
    }

    public void addRole(Long roleId) {
        roles.add(new Role(roleId));
    }
}
