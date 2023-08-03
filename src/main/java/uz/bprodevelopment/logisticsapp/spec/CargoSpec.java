package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Cargo;
import uz.bprodevelopment.logisticsapp.entity.Offer;

import javax.persistence.criteria.*;
import java.util.List;

public class CargoSpec extends BaseSpec<Cargo> {

    private final SearchCriteria criteria;

    public CargoSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Cargo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if ("cargoUserId".equals(criteria.getKey())) {
            Join<User, Cargo> user = root.join("user");
            return builder.equal(user.get("id"), criteria.getValue());
        } else if ("offerUserId".equals(criteria.getKey())) {
            Join<List<Offer>, Cargo> offers = root.join("offers");
            Join<User, Offer> user = offers.join("user");
            return builder.equal(user.get("id"), criteria.getValue());
        } else {
            return super.toPredicate(root, query, builder);
        }
    }
}