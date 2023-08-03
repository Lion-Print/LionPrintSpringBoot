package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.entity.HalfCargoType;
import uz.bprodevelopment.logisticsapp.repo.HalfCargoTypeRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HalfCargoTypeServiceImpl implements HalfCargoTypeService {

    private final HalfCargoTypeRepo repo;

    @Override
    public HalfCargoType getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<HalfCargoType> getListAll(Specification<HalfCargoType> spec, Sort sort) {
        return repo.findAll(spec, sort);
    }

    @Override
    public Page<HalfCargoType> getList(Specification<HalfCargoType> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }

    @Override
    public HalfCargoType save(HalfCargoType item) {
        return repo.save(item);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public HalfCargoType getOneByNameUz(String nameUz) {
        return repo.findByNameUz(nameUz);
    }

}
