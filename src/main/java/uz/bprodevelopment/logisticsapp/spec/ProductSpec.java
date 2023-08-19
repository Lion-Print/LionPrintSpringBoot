package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.*;
import uz.bprodevelopment.logisticsapp.entity.Order;

import javax.persistence.criteria.*;

public class ProductSpec extends BaseSpec<Product> {

    private final SearchCriteria criteria;
    public ProductSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getKey().equals("nameUz")) {
            Join<Category, Product> category = root.join("category");
            return builder.like(builder.lower(category.get("nameUz")),
                    "%" + criteria.getValue().toString().toLowerCase() + "%");
        } else if (criteria.getKey().equals("nameRu")) {
            Join<Category, Product> category = root.join("category");
            return builder.like(builder.lower(category.get("nameRu")),
                    "%" + criteria.getValue().toString().toLowerCase() + "%");
        } else if (criteria.getKey().equals("supplierId")) {
            Join<Supplier, Product> supplier = root.join("supplier");
            return builder.equal(supplier.get("id"), criteria.getValue());
        } else if (criteria.getKey().equals("supplierName")) {
            Join<Supplier, Product> supplier = root.join("supplier");
            return builder.like(builder.lower(supplier.get("name")),
                    "%" + criteria.getValue() + "%");
        }
        else if (criteria.getKey().equals("productDetailValue")) {
            Join<ProductDetail, Product> productDetail = root.join("productDetails");
            return builder.like(builder.lower(productDetail.get("value")),
                    "%" + criteria.getValue() + "%");
        }

        return super.toPredicate(root, query, builder);
    }
}