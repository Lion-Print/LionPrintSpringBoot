package uz.bprodevelopment.logisticsapp.spec;

import uz.bprodevelopment.logisticsapp.entity.Region;

import javax.persistence.criteria.*;

public class RegionSpec extends BaseSpec<Region> {

    private final SearchCriteria criteria;

    public RegionSpec(SearchCriteria criteria) {
        super(criteria);
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if ("regionId".equals(criteria.getKey())) {
            Join<Region, Region> region = root.join("region");
            return builder.equal(region.get("id"), criteria.getValue());
        }
        return super.toPredicate(root, query, builder);
    }
}