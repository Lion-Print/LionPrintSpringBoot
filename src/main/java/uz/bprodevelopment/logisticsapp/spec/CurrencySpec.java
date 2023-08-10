package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Currency;

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

        if (criteria.getKey().equals("companyId")){
            Join<Company, Currency> company = root.join("company");
            return builder.equal(company.get("id"), criteria.getValue());
        } else {
            return super.toPredicate(root, query, builder);
        }
    }
}