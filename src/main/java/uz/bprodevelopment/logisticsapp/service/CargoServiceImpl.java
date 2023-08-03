package uz.bprodevelopment.logisticsapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorMessages;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.file_storage.FileStorageService;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.entity.Cargo;
import uz.bprodevelopment.logisticsapp.entity.Notification;
import uz.bprodevelopment.logisticsapp.entity.Offer;
import uz.bprodevelopment.logisticsapp.repo.CargoRepo;
import uz.bprodevelopment.logisticsapp.repo.OfferRepo;
import uz.bprodevelopment.logisticsapp.spec.CargoSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CargoServiceImpl implements CargoService {

    private final CargoRepo repo;
    private final UserRepo userRepo;
    private final OfferRepo offerRepo;
    private final FileStorageService fileStorageService;
    private final FirebaseMessagingService messagingService;

    @Override
    public Cargo getOne(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<Cargo> getListAll(
            Long offerUserId,
            Long cargoUserId,
            String fromAddress,
            String toAddress,
            Integer truckTypeId,
            Boolean isNew,
            Boolean inProcess,
            Boolean isDelivered,
            String sort
    ) {

        CargoSpec spec1 = new CargoSpec(new SearchCriteria("id", ">", 0));
        Specification<Cargo> spec = Specification.where(spec1);

        if (offerUserId != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("offerUserId", "=", offerUserId)));
        }
        if (cargoUserId != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("cargoUserId", "=", cargoUserId)));
        }

        if (fromAddress != null) {
            CargoSpec specNameUz = new CargoSpec(new SearchCriteria("fromAddressUz", ":", fromAddress));
            CargoSpec specNameRu = new CargoSpec(new SearchCriteria("fromAddressRu", ":", fromAddress));
            CargoSpec specNameBg = new CargoSpec(new SearchCriteria("fromAddressBg", ":", fromAddress));
            spec = spec.and(Specification.where(specNameUz.or(specNameRu.or(specNameBg))));
        }

        if (toAddress != null) {
            CargoSpec specNameUz = new CargoSpec(new SearchCriteria("toAddressUz", ":", toAddress));
            CargoSpec specNameRu = new CargoSpec(new SearchCriteria("toAddressRu", ":", toAddress));
            CargoSpec specNameBg = new CargoSpec(new SearchCriteria("toAddressBg", ":", toAddress));
            spec = spec.and(Specification.where(specNameUz.or(specNameRu.or(specNameBg))));
        }

        if (truckTypeId != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("truckTypeId", "=", truckTypeId)));
        }

        if (isNew != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("status", "=", 1)));
        }

        if (inProcess != null && inProcess) {
            spec = spec.and(new CargoSpec(new SearchCriteria("status", "<", 3)));
        }

        if (isDelivered != null && isDelivered) {
            spec = spec.and(new CargoSpec(new SearchCriteria("status", "=", 3)));
        }

        return repo.findAll(spec, Sort.by(sort).descending());
    }

    @Override
    public Page<Cargo> getList(
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
    ) {

        CargoSpec spec1 = new CargoSpec(new SearchCriteria("id", ">", 0));
        Specification<Cargo> spec = Specification.where(spec1);

        if (offerUserId != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("offerUserId", "=", offerUserId)));
        }
        if (cargoUserId != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("cargoUserId", "=", cargoUserId)));
        }

        if (fromAddress != null) {
            CargoSpec specNameUz = new CargoSpec(new SearchCriteria("fromAddressUz", ":", fromAddress));
            CargoSpec specNameRu = new CargoSpec(new SearchCriteria("fromAddressRu", ":", fromAddress));
            CargoSpec specNameBg = new CargoSpec(new SearchCriteria("fromAddressBg", ":", fromAddress));
            spec = spec.and(Specification.where(specNameUz.or(specNameRu.or(specNameBg))));
        }

        if (toAddress != null) {
            CargoSpec specNameUz = new CargoSpec(new SearchCriteria("toAddressUz", ":", toAddress));
            CargoSpec specNameRu = new CargoSpec(new SearchCriteria("toAddressRu", ":", toAddress));
            CargoSpec specNameBg = new CargoSpec(new SearchCriteria("toAddressBg", ":", toAddress));
            spec = spec.and(Specification.where(specNameUz.or(specNameRu.or(specNameBg))));
        }

        if (truckTypeId != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("truckTypeId", "=", truckTypeId)));
        }

        if (isNew != null) {
            spec = spec.and(new CargoSpec(new SearchCriteria("status", ">", 0)));
            spec = spec.and(new CargoSpec(new SearchCriteria("status", "<", 2)));
        }

        if (inProcess != null && inProcess) {
            spec = spec.and(new CargoSpec(new SearchCriteria("status", "<", 3)));
        }

        if (isDelivered != null && isDelivered) {
            spec = spec.and(new CargoSpec(new SearchCriteria("status", "=", 3)));
        }

        Sort sortBy = Sort.by("status").and(Sort.by("id").descending());
        Pageable pageable = PageRequest.of(page, size, sortBy);

        return repo.findAll(spec, pageable);
    }

    @Override
    public void save(Cargo item) {
        repo.save(item);
        List<String> tokens = userRepo.findByUserUserType(3L);
        Notification notification = new Notification(
                "Yangi yuk",
                "Новый груз",
                "Янги юк",
                item.getFromAddressUz() + " ➔ " + item.getToAddressUz(),
                item.getFromAddressRu() + " ➔ " + item.getToAddressRu(),
                item.getFromAddressBg() + " ➔ " + item.getToAddressBg()
        );
        Runnable runnable = () -> sendNotification(notification, tokens);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void update(Cargo item) {
        repo.save(item);
    }

    @Override
    public void delete(Long id, String lang) {
        User user = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        if (user.getUserType().getId() > 2) {
            throw new IllegalStateException();
        }
        Cargo cargo = repo.getReferenceById(id);
        List<Offer> offers = cargo.getOffers();
        if (offers.stream().anyMatch(offer -> offer.getStatus() > 1)) {
            throw new RuntimeException(ErrorMessages.youCanNotDelete(lang));
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void addOffer(Long cargoId, Offer offer, String lang) {
        Cargo cargo = repo.getReferenceById(cargoId);

        if (cargo.getStatus() == 1) {
            User user = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
            offer.setUser(user);
            cargo.getOffers().add(offer);

            cargo.getOffers().stream().filter(it -> it.getId() > 0);

            if (cargo.getUser().getFcmToken() != null) {
                Notification notification = new Notification(
                        cargo.getFromAddressUz() + " - " + cargo.getToAddressUz(),
                        cargo.getFromAddressRu() + " - " + cargo.getToAddressRu(),
                        cargo.getFromAddressBg() + " - " + cargo.getToAddressBg(),
                        "Yangi taklif tushdi",
                        "Новое предложение тост",
                        "Янги таклиф тушди"
                );
                Runnable runnable = () -> sendNotification(notification, cargo.getUser().getFcmToken());
                Thread thread = new Thread(runnable);
                thread.start();
            }
        } else {
            throw new RuntimeException(ErrorMessages.receivingOfferFinished(lang));
        }
    }

    @Override
    @Transactional
    public void acceptOffer(Long offerId, String lang) {
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        Offer offer = offerRepo.getReferenceById(offerId);
        Cargo cargo = offer.getCargo();
        User cargoUser = cargo.getUser();

        Long currentUserId = currentUser.getId();
        Long cargoUserId = cargoUser.getId();

        if ((cargo.getTruckCount() == null
                && cargo.getOffers().stream().anyMatch(item -> item.getStatus() > 1))
                || (cargo.getTruckCount() != null
                && cargo.getOffers().stream().filter(item -> item.getStatus() > 1).count() >= cargo.getTruckCount())) {
            throw new RuntimeException(ErrorMessages.offerLimit(lang));
        }

        if (!Objects.equals(currentUserId, cargoUserId)
                && currentUser.getUserType().getId() > 2) {
            throw new RuntimeException();
        }
        if (offer.getStatus() != 1) {
            throw new RuntimeException(ErrorMessages.offerNotNewStatus(lang));
        }

        offer.setStatus(2);

        if (cargo.getTruckCount() != null) {
            List<Offer> offers = offerRepo.findAllByStatusAndUserId(1, offer.getUser().getId());
            for (Offer off : offers) {
                off.setStatus(-1);
            }
        }


        if (cargo.getTruckCount() == null
                || cargo.getOffers().stream().filter(item -> item.getStatus() > 1).count() >= cargo.getTruckCount()) {
            cargo.setStatus(2);
        }

        Integer debt = cargoUser.getDebt();
        if (debt != null) {
            cargoUser.setDebt(debt + 50);
        } else {
            cargoUser.setDebt(50);
        }

        if (offer.getUser().getFcmToken() != null) {
            Notification notification = new Notification(offer.getCargo().getFromAddressUz() + " - " + offer.getCargo().getToAddressUz(), offer.getCargo().getFromAddressRu() + " - " + offer.getCargo().getToAddressRu(), offer.getCargo().getFromAddressBg() + " - " + offer.getCargo().getToAddressBg(), "Taklifingiz qabul qilindi", "Ваше предложение принято", "Таклифингиз қабул қилинди");
            Runnable runnable = () -> sendNotification(notification, offer.getUser().getFcmToken());
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @Override
    @Transactional
    public void cancelOffer(Long offerId, Long cancelledUserId, String cancelledDescription) {
        Offer offer = offerRepo.getReferenceById(offerId);
        Cargo cargo = offer.getCargo();
        User cargoUser = cargo.getUser();
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());

        Long currentUserId = currentUser.getId();
        Long offerUserId = offer.getUser().getId();
        Long cargoUserId = cargoUser.getId();

        if (!(Objects.equals(currentUserId, cargoUserId)
                || Objects.equals(currentUserId, offerUserId))
                && currentUser.getUserType().getId() > 2) {
            throw new RuntimeException();
        }
        if (offer.getStatus() > 2) {
            throw new RuntimeException();
        }

        if (offer.getStatus() == 2) {
            Integer cancelledOfferCnt = currentUser.getCancelledOfferCnt();
            if (cancelledOfferCnt == null) cancelledOfferCnt = 0;
            currentUser.setCancelledOfferCnt(cancelledOfferCnt + 1);

            Integer debt = cargoUser.getDebt();
            if (debt != null) {
                cargoUser.setDebt(debt - 50);
            }

            cargo.setStatus(1);
        }

        Integer oldStatus = offer.getStatus();
        offer.setStatus(-1);
        if (oldStatus > 1) {
            User user = new User();
            user.setId(cancelledUserId);
            offer.setCancelledUser(user);
            offer.setCancelledDescription(cancelledDescription);
        }

        if (offer.getUser().getFcmToken() != null) {
            Notification notification = new Notification(
                    offer.getCargo().getFromAddressUz() + " - " + offer.getCargo().getToAddressUz(),
                    offer.getCargo().getFromAddressRu() + " - " + offer.getCargo().getToAddressRu(),
                    offer.getCargo().getFromAddressBg() + " - " + offer.getCargo().getToAddressBg(),
                    "Taklifingiz bekor qilindi",
                    "Ваше предложение отклонено",
                    "Таклифингиз рад қилинди"
            );
            Runnable runnable = () -> sendNotification(notification, offer.getUser().getFcmToken());
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @Override
    @Transactional
    public void deliverOffer(
            Long offerId,
            MultipartFile file) {

        Offer offer = offerRepo.getReferenceById(offerId);
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());

        Long currentUserId = currentUser.getId();
        Long offerUserId = offer.getUser().getId();

        if (!Objects.equals(currentUserId, offerUserId)
                && currentUser.getUserType().getId() > 2) {
            throw new RuntimeException();
        }
        if (offer.getStatus() != 2) {
            throw new RuntimeException();
        }

        try {
            String fileHash = fileStorageService.save(file);
            offer.setDeliveredFileHashId(fileHash);
            offer.setStatus(3);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause().getMessage());
        }

        if (offer.getCargo().getUser().getFcmToken() != null) {
            Notification notification = new Notification(
                    offer.getCargo().getFromAddressUz() + " - " + offer.getCargo().getToAddressUz(),
                    offer.getCargo().getFromAddressRu() + " - " + offer.getCargo().getToAddressRu(),
                    offer.getCargo().getFromAddressBg() + " - " + offer.getCargo().getToAddressBg(),
                    "Yukingiz yetkazildi",
                    "Ваш груз доставлен",
                    "Юкингиз етказилди"
            );
            Runnable runnable = () -> sendNotification(notification, offer.getCargo().getUser().getFcmToken());
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @Override
    @Transactional
    public void receiveOffer(Long offerId) {
        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        Offer offer = offerRepo.getReferenceById(offerId);
        if ((Objects.equals(currentUser.getId(), offer.getCargo().getUser().getId())
                || currentUser.getUserType().getId() < 3)
                && offer.getStatus() == 3) {
            offer.setStatus(4);
        } else {
            throw new RuntimeException();
        }
    }


    @Override
    @Transactional
    public void rateOffer(Long offerId, Long userId, Integer rating) {

        Offer offer = offerRepo.getReferenceById(offerId);
        Cargo cargo = offer.getCargo();

        User currentUser = userRepo.findByUsername(BaseAppUtils.getCurrentUsername());
        User offerUser = offer.getUser();
        User dispatcherUser = offer.getDispatcherUser();
        User cargoUser = cargo.getUser();

        Long currentUserId = currentUser.getId();
        Long offerUserId = offerUser.getId();
        Long dispatcherUserId = dispatcherUser != null ? dispatcherUser.getId() : null;
        Long cargoUserId = cargoUser.getId();

        if (Objects.equals(currentUserId, cargoUserId)
                && Objects.equals(userId, offerUserId)) {

            Double offerUserRating = offerUser.getRating();
            Integer offerUserRatingCnt = offerUser.getRatingCount();
            if (offerUserRating == null || offerUserRatingCnt == null) {
                offerUser.setRatingCount(1);
                offerUser.setRating(1.0 * rating);
            } else {
                Double newRating = (offerUserRating * offerUserRatingCnt + rating) / (offerUserRatingCnt + 1);
                offerUser.setRating(newRating);
                offerUser.setRatingCount(offerUserRatingCnt + 1);
            }
            offer.setRatingOfCargoUserToOfferUser(rating);

        } else if (Objects.equals(currentUserId, offerUserId)
                && Objects.equals(userId, cargoUserId)) {

            Double cargoUserRating = cargoUser.getRating();
            Integer cargoUserRatingCnt = cargoUser.getRatingCount();
            if (cargoUserRating == null || cargoUserRatingCnt == null) {
                cargoUser.setRatingCount(1);
                cargoUser.setRating(1.0 * rating);
            } else {
                Double newRating = (cargoUserRating * cargoUserRatingCnt + rating) / (cargoUserRatingCnt + 1);
                cargoUser.setRating(newRating);
                cargoUser.setRatingCount(cargoUserRatingCnt + 1);
            }
            offer.setRatingOfOfferUserToCargoUser(rating);

        } else if (currentUser.getUserType().getId() < 3
                && Objects.equals(userId, offerUserId)) {

            Double offerUserRating = offerUser.getRating();
            Integer offerUserRatingCnt = offerUser.getRatingCount();
            if (offerUserRating == null || offerUserRatingCnt == null) {
                offerUser.setRatingCount(1);
                offerUser.setRating(1.0 * rating);
            } else {
                Double newRating = (offerUserRating * offerUserRatingCnt + rating) / (offerUserRatingCnt + 1);
                offerUser.setRating(newRating);
                offerUser.setRatingCount(offerUserRatingCnt + 1);
            }
            offer.setRatingOfAdminToOfferUser(rating);

        } else if (currentUser.getUserType().getId() < 3
                && Objects.equals(userId, cargoUserId)) {

            Double cargoUserRating = cargoUser.getRating();
            Integer cargoUserRatingCnt = cargoUser.getRatingCount();
            if (cargoUserRating == null || cargoUserRatingCnt == null) {
                cargoUser.setRatingCount(1);
                cargoUser.setRating(1.0 * rating);
            } else {
                Double newRating = (cargoUserRating * cargoUserRatingCnt + rating) / (cargoUserRatingCnt + 1);
                cargoUser.setRating(newRating);
                cargoUser.setRatingCount(cargoUserRatingCnt + 1);
            }
            offer.setRatingOfAdminToCargoUser(rating);

        } else if (currentUser.getUserType().getId() < 3
                && Objects.equals(userId, dispatcherUserId)) {

            Double dispatcherUserRating = dispatcherUser.getRating();
            Integer dispatcherUserRatingCnt = dispatcherUser.getRatingCount();
            if (dispatcherUserRating == null || dispatcherUserRatingCnt == null) {
                dispatcherUser.setRatingCount(1);
                dispatcherUser.setRating(1.0 * rating);
            } else {
                Double newRating = (dispatcherUserRating * dispatcherUserRatingCnt + rating) / (dispatcherUserRatingCnt + 1);
                dispatcherUser.setRating(newRating);
                dispatcherUser.setRatingCount(dispatcherUserRatingCnt + 1);
            }
            offer.setRatingOfAdminToDispatcherUser(rating);

        }


    }

    @Override
    @Transactional
    public void changeOfferDriver(Long offerId, String username, String lang) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException(ErrorMessages.userNotFound(lang));
        }
        if (user.getUserType().getId() != 3) {
            throw new RuntimeException(ErrorMessages.userIsNotDriver(lang));
        }
        Offer offer = offerRepo.getReferenceById(offerId);
        if (offer.getDispatcherUser() == null) {
            User dispatcherUser = offer.getUser();
            offer.setDispatcherUser(dispatcherUser);
        }
        offer.setUser(user);
    }

    @Override
    @Transactional
    public void setIsOnTheWay(Long cargoId) {
        Cargo cargo = repo.getReferenceById(cargoId);
        cargo.setIsOnTheWay((byte) 1);
        cargo.setStatus(2);
        cargo.getOffers().stream()
                .filter(offer -> offer.getStatus() < 2)
                .forEach(offer -> offer.setStatus(-1));
    }

    @Override
    @Transactional
    public void setDelivered(Long cargoId) {
        Cargo cargo = repo.getReferenceById(cargoId);
        cargo.setStatus(3);
        cargo.setIsOnTheWay((byte) 0);
        cargo.getOffers().stream()
                .filter(offer -> offer.getStatus() == 2)
                .forEach(offer -> offer.setStatus(3));
    }

    public void sendNotification(Notification notification, String token) {
        try {
            String body = new ObjectMapper().writeValueAsString(notification);
            messagingService.sendNotificationSingle(
                    "mm",
                    body,
                    token
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(Notification notification, List<String> tokens) {
        try {
            String body = new ObjectMapper().writeValueAsString(notification);
            messagingService.sendNotificationMultiple(
                    "mm",
                    body,
                    tokens
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
