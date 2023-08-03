package uz.bprodevelopment.logisticsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorMessages;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorResponse;
import uz.bprodevelopment.logisticsapp.dto.RegionCreationDto;
import uz.bprodevelopment.logisticsapp.entity.Region;
import uz.bprodevelopment.logisticsapp.service.RegionService;
import uz.bprodevelopment.logisticsapp.spec.RegionSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import static uz.bprodevelopment.logisticsapp.utils.Urls.REGION_URL;

@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionService service;

    @GetMapping(REGION_URL + "/{id}")
    public ResponseEntity<Region> getOne(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(service.getOne(id));
    }

    @GetMapping(REGION_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "regionId", required = false) Long regionId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sort", required = false) String sort
    ) {

        RegionSpec spec1 = new RegionSpec(new SearchCriteria("id", ">", 0));
        Specification<Region> spec = Specification.where(spec1);

        if (regionId != null){
            spec = spec.and(new RegionSpec(new SearchCriteria("regionId","=", regionId)));
        }

        if (name != null) {
            RegionSpec specNameUz = new RegionSpec(new SearchCriteria("nameUz", ":", name));
            RegionSpec specNameRu = new RegionSpec(new SearchCriteria("nameRu", ":", name));
            RegionSpec specNameBg = new RegionSpec(new SearchCriteria("nameBg", ":", name));
            spec = spec.and(Specification.where(specNameUz.or(specNameRu.or(specNameBg))));
        }

        if (sort == null) sort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return ResponseEntity.ok().body(service.getList(spec, pageable));

    }

    @GetMapping(REGION_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "regionId", required = false) Long regionId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sort", required = false) String sort
    ) {

        RegionSpec spec1 = new RegionSpec(new SearchCriteria("id", ">", 0));
        Specification<Region> spec = Specification.where(spec1);

        if (regionId != null){
            spec = spec.and(new RegionSpec(new SearchCriteria("regionId","=", regionId)));
        }

        if (name != null) {
            RegionSpec specNameUz = new RegionSpec(new SearchCriteria("nameUz", ":", name));
            RegionSpec specNameRu = new RegionSpec(new SearchCriteria("nameRu", ":", name));
            RegionSpec specNameBg = new RegionSpec(new SearchCriteria("nameBg", ":", name));
            spec = spec.and(Specification.where(specNameUz.or(specNameRu.or(specNameBg))));
        }

        if (sort == null) sort = "id";
        Sort sorting = Sort.by(sort).descending();

        return ResponseEntity.ok().body(service.getListAll(spec, sorting));

    }

    @PostMapping(REGION_URL)
    public ResponseEntity<?> save(
            @RequestBody RegionCreationDto item,
            @RequestParam(required = false) String lang
    ) {
        if (item.getNameUz() == null || item.getNameRu() == null
                || item.getNameBg() == null) {
            return ErrorResponse.getInstance().build(ErrorMessages.requiredFieldIsNull(lang));
        }
        service.save(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping(REGION_URL)
    public ResponseEntity<?> update(
            @RequestBody RegionCreationDto item,
            @RequestParam(required = false) String lang
    ) {
        if (item.getNameUz() == null || item.getNameRu() == null
                || item.getNameBg() == null || item.getId() == null) {
            return ErrorResponse.getInstance().build(ErrorMessages.requiredFieldIsNull(lang));
        }
        service.update(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(REGION_URL+"/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}