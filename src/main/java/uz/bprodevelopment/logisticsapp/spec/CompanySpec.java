package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Company;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CompanySpec extends BaseSpec<Company> {

    private final SearchCriteria criteria;

    public CompanySpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return super.toPredicate(root, query, builder);
    }
}