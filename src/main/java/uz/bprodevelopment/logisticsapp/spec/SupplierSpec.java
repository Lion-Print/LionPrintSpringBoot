package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SupplierSpec extends BaseSpec<Supplier> {

    private final SearchCriteria criteria;

    public SupplierSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return super.toPredicate(root, query, builder);
    }
}