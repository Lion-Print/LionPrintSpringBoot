package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.*;
import uz.bprodevelopment.logisticsapp.entity.Order;

import javax.persistence.criteria.*;

public class UserSpec extends BaseSpec<User> {

    private final SearchCriteria criteria;

    public UserSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getKey().equals("companyId")) {
            Join<Company, User> company = root.join("company");
            return builder.equal(company.get("id"), criteria.getValue());
        } else if (criteria.getKey().equals("supplierId")) {
            Join<Supplier, User> supplier = root.join("supplier");
            return builder.equal(supplier.get("id"), criteria.getValue());
        }

        return super.toPredicate(root, query, builder);
    }
}