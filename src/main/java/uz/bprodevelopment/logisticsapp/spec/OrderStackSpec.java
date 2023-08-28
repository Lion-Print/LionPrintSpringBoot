package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.dto.OrderStackDto;
import uz.bprodevelopment.logisticsapp.entity.*;
import uz.bprodevelopment.logisticsapp.entity.Order;

import javax.persistence.criteria.*;

public class OrderStackSpec extends BaseSpec<OrderStack> {

    private final SearchCriteria criteria;

    public OrderStackSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<OrderStack> root, CriteriaQuery<?> query, CriteriaBuilder builder) {


        if(criteria.getKey().equals("fullName")) {
            Join<User, OrderStack> user = root.join("createdBy");
            return builder.like(builder.lower(user.get("fullName")),
                    "%" + criteria.getValue().toString().toLowerCase() + "%");
        }

        return super.toPredicate(root, query, builder);
    }
}