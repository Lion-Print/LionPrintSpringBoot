package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.dto.PaymentDto;
import uz.bprodevelopment.logisticsapp.entity.Payment;
import uz.bprodevelopment.logisticsapp.repo.PaymentRepo;
import uz.bprodevelopment.logisticsapp.spec.PaymentSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo repo;

    @Override
    public Payment getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<Payment> getListAll(
            Long userId,
            Integer amount,
            String sort
    ) {

        PaymentSpec spec1 = new PaymentSpec(new SearchCriteria("id", ">", -1));
        Specification<Payment> spec = Specification.where(spec1);

        if (userId != null)
            spec = spec.and(new PaymentSpec(new SearchCriteria("userId", "=", userId)));
        if (amount != null)
            spec = spec.and(new PaymentSpec(new SearchCriteria("amount", "=", amount)));

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<Payment> getList(
            Integer page,
            Integer size,
            Long userId,
            Integer amount,
            String sort
    ) {

        PaymentSpec spec1 = new PaymentSpec(new SearchCriteria("id", ">", -1));
        Specification<Payment> spec = Specification.where(spec1);

        if (userId != null)
            spec = spec.and(new PaymentSpec(new SearchCriteria("userId", "=", userId)));
        if (amount != null)
            spec = spec.and(new PaymentSpec(new SearchCriteria("amount", "=", amount)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    public void save(PaymentDto item) {
        repo.save(item.toEntity());
    }

    @Override
    public void update(PaymentDto item) {
        repo.save(item.toEntity());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
