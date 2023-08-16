package uz.bprodevelopment.logisticsapp.repo;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.FluentQuery;
import uz.bprodevelopment.logisticsapp.entity.CurrencyType;

import java.util.function.Function;

public interface CurrencyTypeRepo extends JpaRepository<CurrencyType, Long>,
        JpaSpecificationExecutor<CurrencyType> {

    CurrencyType findBySymbol(String symbol);

}
