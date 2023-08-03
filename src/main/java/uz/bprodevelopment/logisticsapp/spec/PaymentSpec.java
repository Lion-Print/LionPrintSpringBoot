package uz.bprodevelopment.logisticsapp.spec;

import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.entity.Payment;

import javax.persistence.criteria.*;

public class PaymentSpec extends BaseSpec<Payment> {

    private final SearchCriteria criteria;

    public PaymentSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if ("userId".equals(criteria.getKey())) {
            Join<User, Payment> user = root.join("userId");
            return builder.equal(user.get("id"), criteria.getValue());
        } else {
            return super.toPredicate(root, query, builder);
        }
    }
}