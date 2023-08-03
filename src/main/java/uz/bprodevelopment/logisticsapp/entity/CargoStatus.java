package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoStatus {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nameUz;
    private String nameRu;
    private String nameBg;

}
