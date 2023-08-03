package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.entity.UserType;
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

        if ("userTypeId".equals(criteria.getKey())) {
            Join<UserType, User> user = root.join("userType");
            if ((Long) criteria.getValue() == 2){
                return builder.lessThanOrEqualTo(user.get("id"), (Long) criteria.getValue());
            } else {
                return builder.equal(user.get("id"), criteria.getValue());
            }
        } else {
            return super.toPredicate(root, query, builder);
        }
    }
}