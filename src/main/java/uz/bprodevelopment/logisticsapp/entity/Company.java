package uz.bprodevelopment.logisticsapp.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.dto.CompanyDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "companies")
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String director;

    private String phone;

    private Boolean isBlocked = false;

    private String userFullName;

    @Column(unique = true, nullable = false)
    private String username;
    public Company(Long id) {
        this.id = id;
    }

    public CompanyDto toDto(){

        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(id);
        companyDto.setName(name);
        companyDto.setDirector(director);
        companyDto.setPhone(phone);
        companyDto.setIsBlocked(isBlocked);
        companyDto.setUserFullName(userFullName);
        companyDto.setUsername(username);

        return companyDto;
    }
}
