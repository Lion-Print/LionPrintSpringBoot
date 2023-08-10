package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private Long id;
    private String name;
    private String director;
    private String phone;
    private Boolean isBlocked = false;

    private String userFullName;
    private String username;
    private String password;

    public Supplier toEntity(){

        Supplier supplier = new Supplier();
        supplier.setId(this.id);
        supplier.setName(this.name);
        supplier.setDirector(this.director);
        supplier.setPhone(this.phone);
        supplier.setIsBlocked(this.isBlocked);
        supplier.setUserFullName(this.userFullName);
        supplier.setUsername(this.username);

        return supplier;
    }

}
