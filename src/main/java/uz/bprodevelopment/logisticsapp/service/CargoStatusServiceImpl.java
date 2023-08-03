package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.entity.CargoStatus;
import uz.bprodevelopment.logisticsapp.repo.CargoStatusRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CargoStatusServiceImpl implements CargoStatusService {

    private final CargoStatusRepo repo;

    @Override
    public CargoStatus getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<CargoStatus> getListAll(Specification<CargoStatus> spec, Sort sort) {
        return repo.findAll(spec, sort);
    }

    @Override
    public Page<CargoStatus> getList(Specification<CargoStatus> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }

    @Override
    public CargoStatus save(CargoStatus item) {
        return repo.save(item);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public CargoStatus getOneByNameUz(String nameUz) {
        return repo.findByNameUz(nameUz);
    }
}
