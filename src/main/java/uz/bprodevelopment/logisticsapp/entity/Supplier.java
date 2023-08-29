package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "suppliers")
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String director;

    private String phone;

    private Boolean isBlocked = false;

    private String userFullName;

    private String userPhone;

    @Column(unique = true, nullable = false)
    private String username;
    public Supplier(Long id) {
        this.id = id;
    }

    public SupplierDto toDto(){

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(id);
        supplierDto.setName(name);
        supplierDto.setDirector(director);
        supplierDto.setPhone(phone);
        supplierDto.setIsBlocked(isBlocked);
        supplierDto.setUserFullName(userFullName);
        supplierDto.setUserPhone(userPhone);
        supplierDto.setUsername(username);

        return supplierDto;
    }
}
