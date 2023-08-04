package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.base.entity.User;

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

        return super.toPredicate(root, query, builder);
    }
}