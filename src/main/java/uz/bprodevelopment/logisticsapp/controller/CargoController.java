package uz.bprodevelopment.logisticsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.bprodevelopment.logisticsapp.dto.CargoCreationDto;
import uz.bprodevelopment.logisticsapp.entity.Cargo;
import uz.bprodevelopment.logisticsapp.entity.Offer;
import uz.bprodevelopment.logisticsapp.service.CargoService;

import static uz.bprodevelopment.logisticsapp.utils.Urls.CARGO_URL;

@RestController
@RequiredArgsConstructor
public class CargoController {

    private final CargoService service;

    @GetMapping(CARGO_URL + "/{id}")
    public ResponseEntity<Cargo> getOne(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(service.getOne(id));
    }

    @GetMapping(CARGO_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "offerUserId", required = false) Long offerUserId,
            @RequestParam(name = "cargoUserId", required = false) Long cargoUserId,
            @RequestParam(name = "fromAddress", required = false) String fromAddress,
            @RequestParam(name = "toAddress", required = false) String toAddress,
            @RequestParam(name = "truckTypeId", required = false) Integer truckTypeId,
            @RequestParam(name = "isNew", required = false) Boolean isNew,
            @RequestParam(name = "inProcess", required = false) Boolean inProcess,
            @RequestParam(name = "isDelivered", required = false) Boolean isDelivered,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {

        return ResponseEntity.ok().body(service.getList(
                page, size, offerUserId, cargoUserId, fromAddress,
                toAddress, truckTypeId, isNew, inProcess, isDelivered, sort)
        );

    }

    @GetMapping(CARGO_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "offerUserId", required = false) Long offerUserId,
            @RequestParam(name = "cargoUserId", required = false) Long cargoUserId,
            @RequestParam(name = "fromAddress", required = false) String fromAddress,
            @RequestParam(name = "toAddress", required = false) String toAddress,
            @RequestParam(name = "truckTypeId", required = false) Integer truckTypeId,
            @RequestParam(name = "isNew", required = false) Boolean isNew,
            @RequestParam(name = "inProcess", required = false) Boolean inProcess,
            @RequestParam(name = "isDelivered", required = false) Boolean isDelivered,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {

        return ResponseEntity.ok().body(service.getListAll(
                offerUserId, cargoUserId, fromAddress, toAddress, truckTypeId,
                isNew, inProcess, isDelivered, sort
        ));

    }

    @PostMapping(CARGO_URL)
    public ResponseEntity<?> save(
            @RequestBody CargoCreationDto item,
            @RequestHeader(name = "lang", required = false, defaultValue = "ru") String lang
    ) {
        service.save(item.toEntity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(CARGO_URL + "/{id}")
    public ResponseEntity<?> save(
            @PathVariable(name = "id") Long id,
            @RequestHeader(name = "lang", required = false, defaultValue = "ru") String lang
    ) {
        service.delete(id, lang);
        return ResponseEntity.ok().build();
    }


    @PostMapping(CARGO_URL + "/{id}")
    public ResponseEntity<?> addOffer(
            @PathVariable(name = "id") Long id,
            @RequestBody Offer offer,
            @RequestHeader(name = "lang", required = false, defaultValue = "ru") String lang
    ) {
        service.addOffer(id, offer, lang);
        return ResponseEntity.ok().build();
    }

    @PostMapping(CARGO_URL + "/accept-offer/{offerId}")
    public ResponseEntity<?> acceptOffer(
            @PathVariable(name = "offerId") Long offerId,
            @RequestHeader(name = "lang", required = false, defaultValue = "ru") String lang
    ) {
        service.acceptOffer(offerId, lang);
        return ResponseEntity.ok().build();
    }

    @PostMapping(CARGO_URL + "/cancel-offer/{offerId}")
    public ResponseEntity<?> cancelOffer(
            @PathVariable(name = "offerId") Long offerId,
            @RequestParam(name = "cancelledUserId", required = false) Long cancelledUserId,
            @RequestParam(name = "cancelledDescription", required = false) String cancelledDescription
    ) {
        service.cancelOffer(offerId, cancelledUserId, cancelledDescription);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = CARGO_URL + "/deliver-offer/{offerId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> deliverOffer(
            @PathVariable(name = "offerId") Long offerId,
            @RequestPart MultipartFile file
    ) {
        service.deliverOffer(offerId, file);
        return ResponseEntity.ok().build();
    }

    @PostMapping(CARGO_URL + "/receive-offer/{offerId}")
    public ResponseEntity<?> receiveOffer(
            @PathVariable(name = "offerId") Long offerId
    ) {
        service.receiveOffer(offerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(CARGO_URL + "/rate-offer/{offerId}")
    public ResponseEntity<?> rateOffer(
            @PathVariable(name = "offerId") Long offerId,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "rating") Integer rating
    ) {
        service.rateOffer(offerId, userId, rating);
        return ResponseEntity.ok().build();
    }

    @PostMapping(CARGO_URL + "/change-offer-driver/{offerId}")
    public ResponseEntity<?> changeOfferDriver(
            @PathVariable(name = "offerId") Long offerId,
            @RequestParam(name = "username") String username,
            @RequestHeader(name = "lang") String lang
    ) {
        service.changeOfferDriver(offerId, username, lang);
        return ResponseEntity.ok().build();
    }

    @PostMapping(CARGO_URL + "/on-the-way/{cargoId}")
    public ResponseEntity<?> setIsOnTheWay(
            @PathVariable(name = "cargoId") Long cargoId
    ) {
        service.setIsOnTheWay(cargoId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(CARGO_URL + "/delivered/{cargoId}")
    public ResponseEntity<?> setDelivered(
            @PathVariable(name = "cargoId") Long cargoId
    ) {
        service.setDelivered(cargoId);
        return ResponseEntity.ok().build();
    }


}