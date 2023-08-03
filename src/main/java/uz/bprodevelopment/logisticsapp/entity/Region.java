package uz.bprodevelopment.logisticsapp.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import uz.bprodevelopment.logisticsapp.base.entity.BaseAuditEntity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_bg")
    private String nameBg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Region region;

    @OneToMany(mappedBy = "region", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Region> regions = new ArrayList<>();

    private Byte isCountry;
    private Byte isRegion;
    private Byte isDistrict;

    private Double latitude;
    private Double longitude;

    @Column(name = "is_top") private Byte isTop;
}
