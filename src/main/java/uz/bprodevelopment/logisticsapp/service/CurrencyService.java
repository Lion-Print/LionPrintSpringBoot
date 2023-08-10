package uz.bprodevelopment.logisticsapp.service;


import uz.bprodevelopment.logisticsapp.dto.CurrencyDto;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface CurrencyService {

    CurrencyDto getOne(Long id);

    List<CurrencyDto> getListAll(
            Long companyId,
            String sort
    );

    CustomPage<CurrencyDto> getList(
            Integer page,
            Integer size,
            Long companyId,
            String sort
    );

    void save(CurrencyDto item);

    void update(CurrencyDto item);

    void delete(Long id);

}
