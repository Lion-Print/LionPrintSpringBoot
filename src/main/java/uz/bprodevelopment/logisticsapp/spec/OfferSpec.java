package uz.bprodevelopment.logisticsapp.spec;

import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Cargo;
import uz.bprodevelopment.logisticsapp.entity.Offer;

import javax.persistence.criteria.*;

public class OfferSpec extends BaseSpec<Offer> {

    private final SearchCriteria criteria;

    public OfferSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Offer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getKey()) {
            case "cargoId":
                Join<Cargo, Offer> cargo = root.join("cargo");
                return builder.equal(cargo.get("id"), criteria.getValue());
            case "driverUserId":
                Join<User, Offer> driverUser = root.join("driverUser");
                return builder.equal(driverUser.get("id"), criteria.getValue());
            default:
                return super.toPredicate(root, query, builder);
        }
    }
}