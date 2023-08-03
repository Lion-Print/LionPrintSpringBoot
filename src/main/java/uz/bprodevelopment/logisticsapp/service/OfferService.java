package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.logisticsapp.entity.Offer;


public interface OfferService {

    Offer saveOffer(Offer offer);

    Page<Offer> getOffers(
            Integer page,
            Integer size,
            Long cargoId,
            Long driverUserId,
            Integer accepted
    );

}
