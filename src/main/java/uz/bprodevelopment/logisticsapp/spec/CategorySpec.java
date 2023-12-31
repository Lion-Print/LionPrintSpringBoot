package uz.bprodevelopment.logisticsapp.spec;


import uz.bprodevelopment.logisticsapp.entity.Category;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CategorySpec extends BaseSpec<Category> {

    public CategorySpec(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate
            (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return super.toPredicate(root, query, builder);
    }
}