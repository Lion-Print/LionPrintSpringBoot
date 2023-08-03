package uz.bprodevelopment.logisticsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.logisticsapp.entity.Region;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionCreationDto {

    private Long id;
    private String nameUz;
    private String nameRu;
    private String nameBg;
    private Long regionId;

    private Byte isCountry;
    private Byte isRegion;
    private Byte isDistrict;
    private Byte isTop = 1;

    private Double latitude;
    private Double longitude;

    public Region toEntity(){

        Region region = new Region();
        region.setId(this.id);
        region.setNameUz(this.nameUz);
        region.setNameBg(this.nameBg);
        region.setNameRu(this.nameRu);
        region.setIsCountry(this.isCountry);
        region.setIsRegion(this.isRegion);
        region.setIsDistrict(this.isDistrict);
        region.setIsTop(this.isTop);
        region.setLatitude(this.latitude);
        region.setLongitude(this.longitude);

        Region reg = new Region();
        reg.setId(this.regionId);

        region.setRegion(reg.getId() == null ? null : reg);

        return region;

    }

}
