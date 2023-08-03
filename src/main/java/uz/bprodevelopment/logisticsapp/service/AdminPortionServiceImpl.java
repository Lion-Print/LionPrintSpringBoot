package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.entity.AdminPortion;
import uz.bprodevelopment.logisticsapp.entity.TruckType;
import uz.bprodevelopment.logisticsapp.repo.AdminPortionRepo;
import uz.bprodevelopment.logisticsapp.repo.TruckTypeRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminPortionServiceImpl implements AdminPortionService {

    private final AdminPortionRepo repo;

    @Override
    public List<AdminPortion> getListAll() {
        return repo.findAll();
    }

    @Override
    public AdminPortion save(AdminPortion item) {
        return repo.save(item);
    }

    @Override
    public AdminPortion getOneByCurrencyTypeId(Long currencyTypeId) {
        return repo.findByCurrencyTypeId(currencyTypeId);
    }
}
