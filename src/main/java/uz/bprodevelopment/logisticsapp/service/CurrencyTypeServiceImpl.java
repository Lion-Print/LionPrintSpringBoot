package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;
import uz.bprodevelopment.logisticsapp.repo.CurrencyTypeRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CurrencyTypeServiceImpl implements CurrencyTypeService {

    private final CurrencyTypeRepo repo;

    @Override
    public CurrencyType getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<CurrencyType> getListAll(Specification<CurrencyType> spec, Sort sort) {
        return repo.findAll(spec, sort);
    }

    @Override
    public Page<CurrencyType> getList(Specification<CurrencyType> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }

    @Override
    public CurrencyType save(CurrencyType item) {
        return repo.save(item);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public CurrencyType getOneByNameUz(String nameUz) {
        return repo.findByNameUz(nameUz);
    }

}
