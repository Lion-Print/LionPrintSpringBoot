package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.*;
import uz.bprodevelopment.logisticsapp.entity.Order;

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

        switch (criteria.getKey()) {
            case "nameUz": {
                Join<Product, Order> product = root.join("product");
                Join<Category, Product> category = product.join("category");
                return builder.like(builder.lower(category.get("nameUz")),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            }

            case "nameRu": {
                Join<Product, Order> product = root.join("product");
                Join<Category, Product> category = product.join("category");
                return builder.like(builder.lower(category.get("nameRu")),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            }

            case "supplierId":
                Join<Supplier, Order> supplier = root.join("supplier");
                return builder.equal(supplier.get("id"), criteria.getValue());

            case "companyId":
                Join<Supplier, Order> company = root.join("company");
                return builder.equal(company.get("id"), criteria.getValue());
        }

        return super.toPredicate(root, query, builder);
    }
}