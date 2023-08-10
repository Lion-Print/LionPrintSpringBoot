package uz.bprodevelopment.logisticsapp.service;


import uz.bprodevelopment.logisticsapp.dto.CurrencyTypeDto;
import uz.bprodevelopment.logisticsapp.utils.CustomPage;

import java.util.List;

public interface CurrencyTypeService {

    CurrencyTypeDto getOne(Long id);

    List<CurrencyTypeDto> getListAll(
            String name,
            String sort
    );

    CustomPage<CurrencyTypeDto> getList(
            Integer page,
            Integer size,
            String name,
            String sort
    );

    void save(CurrencyTypeDto item);

    void update(CurrencyTypeDto item);

    void delete(Long id);

}
