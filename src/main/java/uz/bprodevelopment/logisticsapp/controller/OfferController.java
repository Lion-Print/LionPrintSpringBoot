package uz.bprodevelopment.logisticsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.entity.Offer;
import uz.bprodevelopment.logisticsapp.service.OfferService;

import static uz.bprodevelopment.logisticsapp.utils.Urls.OFFER_URL;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping(OFFER_URL)
    public ResponseEntity<?> getOffers(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "cargoId", required = false) Long cargoId,
            @RequestParam(name = "driverUserId", required = false) Long driverUserId,
            @RequestParam(name = "accepted", required = false) Integer accepted
    ) {
        return ResponseEntity.ok().body(offerService.getOffers(
                page,
                size,
                cargoId,
                driverUserId,
                accepted
        ));
    }

    @PostMapping(OFFER_URL)
    public ResponseEntity<?> saveCargo(@RequestBody Offer offer) {
        Offer newCargo = offerService.saveOffer(offer);
        return ResponseEntity.ok().body(newCargo);
    }

}