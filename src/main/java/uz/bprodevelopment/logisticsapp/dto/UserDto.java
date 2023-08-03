package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.entity.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String fullName;
    private String username;
    private String password;
    private Long roleId;
    private Long userTypeId;
    private Long carrierTypeId;;

    public User toEntity(){

        UserType userType = new UserType();
        userType.setId(userTypeId);

        Role role = new Role();
        if (this.roleId == null){
            role = null;
        }else{
            role.setId(this.roleId);
        }

        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFullName(this.fullName);
        user.setRole(role);
        user.setUserType(userType);
        user.setCarrierTypeId(this.carrierTypeId);

        return user;
    }

}
