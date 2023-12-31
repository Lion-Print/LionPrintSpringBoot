package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.Role;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Company;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    private String name;
    private String director;
    private String phone;
    private Boolean isBlocked = false;

    private String userFullName;
    private String userPhone;
    private String username;
    private String password;

    public Company toEntity(){

        Company company = new Company();
        company.setId(this.id);
        company.setName(this.name);
        company.setDirector(this.director);
        company.setPhone(this.phone);
        company.setIsBlocked(this.isBlocked);
        company.setUserFullName(this.userFullName);
        company.setUserPhone(this.userPhone);
        company.setUsername(this.username);

        return company;
    }

}
