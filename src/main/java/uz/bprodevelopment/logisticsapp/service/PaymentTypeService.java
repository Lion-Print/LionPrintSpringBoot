package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.bprodevelopment.logisticsapp.entity.PaymentType;

import java.util.List;


public interface PaymentTypeService {

    PaymentType getOne(Long id);

    List<PaymentType> getListAll(Specification<PaymentType> specification, Sort sorting);

    Page<PaymentType> getList(Specification<PaymentType> specification, Pageable pageable);

    PaymentType save(PaymentType item);

    void delete(Long id);

    PaymentType getOneByNameUz(String nameUz);

}
