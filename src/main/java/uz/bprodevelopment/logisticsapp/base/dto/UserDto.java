package uz.bprodevelopment.logisticsapp.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String fullName;
    private String username;
    private String phone;
    private String password;
    private String fcmToken;
    private Boolean isBlocked = false;

    private Long roleId;
    private Set<Role> roles;

    private Long companyId;
    private String companyName;
    private Long supplierId;
    private String supplierName;

    public User toEntity(){

        User user = new User();
        user.setId(this.id);
        user.setFullName(this.fullName);
        user.setUsername(this.username);
        user.setPhone(this.phone);
        user.setPassword(this.password);
        user.setFcmToken(this.fcmToken);

        return user;
    }

}
