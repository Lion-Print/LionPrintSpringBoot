package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import uz.bprodevelopment.logisticsapp.entity.Cargo;
import uz.bprodevelopment.logisticsapp.entity.Offer;

import java.util.List;


public interface CargoService {

    Cargo getOne(Long id);

    List<Cargo> getListAll(
            Long offerUserId,
            Long cargoUserId,
            String fromAddress,
            String toAddress,
            Integer truckTypeId,
            Boolean isNew,
            Boolean inProcess,
            Boolean isDelivered,
            String sort
    );

    Page<Cargo> getList(
            Integer page,
            Integer size,
            Long offerUserId,
            Long cargoUserId,
            String fromAddress,
            String toAddress,
            Integer truckTypeId,
            Boolean isNew,
            Boolean inProcess,
            Boolean isDelivered,
            String sort
    );

    void save(Cargo item);

    void update(Cargo item);

    void delete(Long id, String lang);

    void addOffer(Long cargoId, Offer offer, String lang);

    void acceptOffer(Long offerId, String lang);

    void cancelOffer(Long offerId, Long cancelledUserId, String cancelledDescription);

    void deliverOffer(Long offerId, MultipartFile file);

    void receiveOffer(Long offerId);

    void rateOffer(Long offerId, Long userId, Integer rating);

    void changeOfferDriver(Long offerId, String username, String lang);

    void setIsOnTheWay(Long cargoId);

    void setDelivered(Long cargoId);

}
