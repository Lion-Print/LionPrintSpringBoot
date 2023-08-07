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
import uz.bprodevelopment.logisticsapp.entity.CompanyProduct;
import uz.bprodevelopment.logisticsapp.repo.CompanyProductRepo;
import uz.bprodevelopment.logisticsapp.spec.CompanyProductSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyProductServiceImpl implements CompanyProductService {

    private final CompanyProductRepo repo;

    @Override
    public CompanyProduct getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public List<CompanyProduct> getListAll(
            String name,
            String sort
    ) {
        CompanyProductSpec spec1 = new CompanyProductSpec(new SearchCriteria("id", ">", 0));
        Specification<CompanyProduct> spec = Specification.where(spec1);

        if (name != null) {
            spec = spec.and(new CompanyProductSpec(new SearchCriteria("name", ":", name)));
        }

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<CompanyProduct> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    ) {

        CompanyProductSpec spec1 = new CompanyProductSpec(new SearchCriteria("id", ">", 0));
        Specification<CompanyProduct> spec = Specification.where(spec1);

        if (name != null) {
            spec = spec.and(new CompanyProductSpec(new SearchCriteria("name", ":", name)));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public void save(CompanyProduct item) {

        if(item.getName() == null) {
            throw new RuntimeException("Maxsulot nomini kiriting");
        }

        repo.save(item);

    }

    @Override
    @Transactional
    public void update(CompanyProduct item) {

        if(item.getName() == null) {
            throw new RuntimeException("Maxsulot nomini kiriting");
        }

        repo.save(item);

    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
