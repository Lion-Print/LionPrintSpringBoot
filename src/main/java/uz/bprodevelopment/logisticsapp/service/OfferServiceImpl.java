package uz.bprodevelopment.logisticsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.bprodevelopment.logisticsapp.entity.Offer;
import uz.bprodevelopment.logisticsapp.repo.OfferRepo;
import uz.bprodevelopment.logisticsapp.spec.OfferSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OfferServiceImpl implements OfferService {

    private final OfferRepo repo;

    @Override
    public Offer saveOffer(Offer offer) {

        return repo.save(offer);
    }

    @Override
    public Page<Offer> getOffers(
            Integer page,
            Integer size,
            Long cargoId,
            Long driverUserId,
            Integer accepted
    ) {

        OfferSpec spec1 = new OfferSpec(new SearchCriteria("id", ">", -1));
        Specification<Offer> spec = Specification.where(spec1);

        if (cargoId != null)
            spec = spec.and(new OfferSpec(new SearchCriteria("cargoId", "=", cargoId)));
        if (driverUserId != null)
            spec = spec.and(new OfferSpec(new SearchCriteria("driverUserId", "=", driverUserId)));
        if (accepted != null)
            spec = spec.and(new OfferSpec(new SearchCriteria("accepted", "=", accepted)));

        Sort sort = Sort.by(
                Sort.Order.desc("accepted"),
                Sort.Order.desc("id")
        );

        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(spec, pageable);
    }

}
