package uz.bprodevelopment.logisticsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetail extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nameUz;
    private String nameRu;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

}
