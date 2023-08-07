package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;

import javax.persistence.*;

import java.util.List;

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

    @ManyToMany
    private List<Company> companies;

}
