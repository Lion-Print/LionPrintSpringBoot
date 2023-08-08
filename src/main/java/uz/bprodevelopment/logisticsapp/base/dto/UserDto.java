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

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String fcmToken;
    private Long roleId;
    private String roleName;

    private Company company;
    private Supplier supplier;

    public User toEntity(){

        User user = new User();
        user.setId(this.id);
        user.setFullName(this.fullName);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFcmToken(this.fcmToken);
        user.setRole(new Role(roleId));

        return user;
    }

}
