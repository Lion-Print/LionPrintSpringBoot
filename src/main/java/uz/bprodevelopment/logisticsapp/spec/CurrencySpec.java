package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Currency;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import javax.persistence.criteria.*;

public class CurrencySpec extends BaseSpec<Currency> {

    private final SearchCriteria criteria;
    public CurrencySpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Currency> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getKey().equals("supplierId")){
            Join<Supplier, Currency> supplier = root.join("supplier");
            return builder.equal(supplier.get("id"), criteria.getValue());
        } else {
            return super.toPredicate(root, query, builder);
        }
    }
}