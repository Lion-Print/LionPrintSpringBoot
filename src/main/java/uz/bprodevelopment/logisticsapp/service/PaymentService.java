package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.dto.PaymentDto;
import uz.bprodevelopment.logisticsapp.entity.Payment;

import java.util.List;


public interface PaymentService {

    Payment getOne(Long id);

    List<Payment> getListAll(
            Long userId,
            Integer amount,
            String sort
    );

    Page<Payment> getList(
            Integer page,
            Integer size,
            Long userId,
            Integer amount,
            String sort
    );

    void save(PaymentDto item);

    void update(PaymentDto item);

    void delete(Long id);

}
