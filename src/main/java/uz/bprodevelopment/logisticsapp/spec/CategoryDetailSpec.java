package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.Category;
import uz.bprodevelopment.logisticsapp.entity.CategoryDetail;
import uz.bprodevelopment.logisticsapp.entity.Company;
import uz.bprodevelopment.logisticsapp.entity.Supplier;

import javax.persistence.criteria.*;

public class CategoryDetailSpec extends BaseSpec<CategoryDetail> {

    private final SearchCriteria criteria;

    public CategoryDetailSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<CategoryDetail> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getKey().equals("categoryId")) {
            Join<Category, CategoryDetail> category = root.join("category");
            return builder.equal(category.get("id"), criteria.getValue());
        } else {
            return super.toPredicate(root, query, builder);
        }
    }
}