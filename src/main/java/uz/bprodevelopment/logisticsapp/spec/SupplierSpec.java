package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import javax.persistence.criteria.*;

public class SupplierSpec extends BaseSpec<Supplier> {

    private final SearchCriteria criteria;

    public SupplierSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getKey().equals("companyId")) {
            Join<Company, Supplier> company = root.join("company");
            return builder.equal(company.get("id"), criteria.getValue());
        } else {
            return super.toPredicate(root, query, builder);
        }
    }
}