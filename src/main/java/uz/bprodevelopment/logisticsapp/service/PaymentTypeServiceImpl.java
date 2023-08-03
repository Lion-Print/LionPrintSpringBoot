package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.entity.PaymentType;
import uz.bprodevelopment.logisticsapp.repo.PaymentTypeRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepo repo;

    @Override
    public PaymentType getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<PaymentType> getListAll(Specification<PaymentType> spec, Sort sort) {
        return repo.findAll(spec, sort);
    }

    @Override
    public Page<PaymentType> getList(Specification<PaymentType> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }

    @Override
    public PaymentType save(PaymentType item) {
        return repo.save(item);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public PaymentType getOneByNameUz(String nameUz) {
        return repo.findByNameUz(nameUz);
    }
}
