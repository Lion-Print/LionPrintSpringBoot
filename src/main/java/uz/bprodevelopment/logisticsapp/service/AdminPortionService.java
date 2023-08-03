package uz.bprodevelopment.logisticsapp.service;

import uz.bprodevelopment.logisticsapp.entity.AdminPortion;

import java.util.List;


public interface AdminPortionService {

    List<AdminPortion> getListAll();

    AdminPortion save(AdminPortion item);

    AdminPortion getOneByCurrencyTypeId(Long currencyTypeId);

}
