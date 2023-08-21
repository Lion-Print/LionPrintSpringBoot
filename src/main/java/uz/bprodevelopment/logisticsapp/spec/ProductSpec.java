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

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Predicate toPredicate
            (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getKey()) {
            case "nameUz": {
                Join<Category, Product> category = root.join("category");
                return builder.like(builder.lower(category.get("nameUz")),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            }
            case "nameRu": {
                Join<Category, Product> category = root.join("category");
                return builder.like(builder.lower(category.get("nameRu")),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            }
            case "supplierId": {
                Join<Supplier, Product> supplier = root.join("supplier");
                return builder.equal(supplier.get("id"), criteria.getValue());
            }
            case "supplierName": {
                Join<Supplier, Product> supplier = root.join("supplier");
                return builder.like(builder.lower(supplier.get("name")),
                        "%" + criteria.getValue() + "%");
            }
            case "productDetailValue":
                //Join<ProductDetail, Product> productDetail = root.join("productDetails");
                //I got duplicate values cause of my code was like above
                // So I change my code fetch like below and duplicates gone.
                Join productDetail = (Join) root.fetch("productDetails");
                return builder.like(builder.lower(productDetail.get("value")),
                        "%" + criteria.getValue() + "%");
        }

        return super.toPredicate(root, query, builder);
    }
}