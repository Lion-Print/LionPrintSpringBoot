package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.entity.Order;
import uz.bprodevelopment.logisticsapp.entity.Product;
import uz.bprodevelopment.logisticsapp.entity.ProductDetail;

import javax.persistence.criteria.*;

public class OrderSpec extends BaseSpec<Order> {

    private final SearchCriteria criteria;
    public OrderSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getKey().equals("productId")) {
            Join<Product, Order> product = root.join("product");
            return builder.equal(product.get("id"), criteria.getValue());
        }

        return super.toPredicate(root, query, builder);
    }
}