package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.logisticsapp.entity.Currency;

import java.util.List;

public interface CurrencyRepo extends JpaRepository<Currency, Long>,
        JpaSpecificationExecutor<Currency> {

    List<Currency> findAllByCurrencyTypeIdAndCompanyId(Long currencyTypeId, Long companyId);

}
