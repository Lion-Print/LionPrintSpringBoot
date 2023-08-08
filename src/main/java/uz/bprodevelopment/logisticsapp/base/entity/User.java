package uz.bprodevelopment.logisticsapp.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.dto.UserDto;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

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
    private Company company;

    @OneToOne
    private Supplier supplier;

    public UserDto toDto(){

        UserDto userDto = new UserDto();
        userDto.setId(this.id);
        userDto.setFullName(this.fullName);
        userDto.setUsername(this.username);
        userDto.setRoleId(this.role.getId());
        userDto.setRoleName(this.role.getName());

        return userDto;
    }
}
