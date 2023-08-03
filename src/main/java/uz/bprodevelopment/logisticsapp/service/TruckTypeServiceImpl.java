package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.entity.TruckType;
import uz.bprodevelopment.logisticsapp.repo.TruckTypeRepo;
import uz.bprodevelopment.logisticsapp.repo.TruckTypeRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TruckTypeServiceImpl implements TruckTypeService {

    private final TruckTypeRepo repo;

    @Override
    public TruckType getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<TruckType> getListAll(Specification<TruckType> spec, Sort sort) {
        return repo.findAll(spec, sort);
    }

    @Override
    public Page<TruckType> getList(Specification<TruckType> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }

    @Override
    public TruckType save(TruckType item) {
        return repo.save(item);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public TruckType getOneByNameUz(String nameUz) {
        return repo.findByNameUz(nameUz);
    }

}
