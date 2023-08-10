package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.CurrencyType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CurrencyTypeSpec extends BaseSpec<CurrencyType> {

    public CurrencyTypeSpec(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate
            (Root<CurrencyType> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return super.toPredicate(root, query, builder);
    }
}