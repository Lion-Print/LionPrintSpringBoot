package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.dto.RegionCreationDto;
import uz.bprodevelopment.logisticsapp.entity.Region;
import uz.bprodevelopment.logisticsapp.repo.RegionRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionServiceImpl implements RegionService {

    private final RegionRepo repo;

    @Override
    public Region getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<Region> getByNameUz(String nameUz) {
        return repo.findByNameUz(nameUz);
    }

    @Override
    public List<Region> getListAll(Specification<Region> spec, Sort sort) {
        return repo.findAll(spec, sort);
    }

    @Override
    public Page<Region> getList(Specification<Region> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }

    @Override
    public void save(RegionCreationDto item) {
        Region region = item.toEntity();
        repo.save(region);
    }

    @Override
    public void update(RegionCreationDto item) {
        repo.save(item.toEntity());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
