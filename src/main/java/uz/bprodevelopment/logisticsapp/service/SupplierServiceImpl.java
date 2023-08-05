package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.logisticsapp.dto.SupplierDto;
import uz.bprodevelopment.logisticsapp.entity.Supplier;
import uz.bprodevelopment.logisticsapp.repo.SupplierRepo;
import uz.bprodevelopment.logisticsapp.spec.SupplierSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepo repo;
    @Override
    public Supplier getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Supplier> getListAll(
            String name,
            String director,
            String phone,
            String sort
    ) {
        SupplierSpec spec1 = new SupplierSpec(new SearchCriteria("id", ">", 0));
        Specification<Supplier> spec = Specification.where(spec1);

        if (name != null) {
            spec = spec.and(new SupplierSpec(new SearchCriteria("name", ":", name)));
        } else if (director != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("director", ":", director)));
        else if (phone != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("phone", ":", phone)));

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<Supplier> getList(
            Integer page,
            Integer size,
            String name,
            String director,
            String phone,
            String sort
    ) {

        SupplierSpec spec1 = new SupplierSpec(new SearchCriteria("id", ">", 0));
        Specification<Supplier> spec = Specification.where(spec1);

        if (name != null) {
            spec = spec.and(new SupplierSpec(new SearchCriteria("name", ":", name)));
        } else if (director != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("director", ":", director)));
        else if (phone != null)
            spec = spec.and(new SupplierSpec(new SearchCriteria("phone", ":", phone)));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    public void save(SupplierDto item) {
        Supplier supplier = item.toEntity();
        repo.save(supplier);
    }

    @Override
    @Transactional
    public void update(SupplierDto item) {
        repo.save(item.toEntity());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
