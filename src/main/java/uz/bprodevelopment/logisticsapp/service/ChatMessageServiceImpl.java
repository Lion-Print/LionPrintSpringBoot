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
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.file_storage.FileStorageService;
import uz.bprodevelopment.logisticsapp.base.repo.UserRepo;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.entity.ChatMessage;
import uz.bprodevelopment.logisticsapp.entity.Notification;
import uz.bprodevelopment.logisticsapp.entity.Offer;
import uz.bprodevelopment.logisticsapp.repo.ChatMessageRepo;
import uz.bprodevelopment.logisticsapp.repo.OfferRepo;
import uz.bprodevelopment.logisticsapp.spec.BaseSpec;
import uz.bprodevelopment.logisticsapp.spec.SearchCriteria;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepo repo;
    private final UserRepo userRepo;
    private final OfferRepo offerRepo;

    private final FileStorageService fileStorageService;
    private final FirebaseMessagingService messagingService;

    @Override
    public ChatMessage saveChatTextMessage(
            String message,
            Long offerId,
            Long cargoId
    ) {
        String username = BaseAppUtils.getCurrentUsername();
        User user = userRepo.findByUsername(username);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setOfferId(offerId);
        chatMessage.setCargoId(cargoId);
        chatMessage.setUser(user);
        chatMessage.setMessage(message);
        chatMessage.setMessageType(1);

        Offer offer = offerRepo.getReferenceById(offerId);
        if (!Objects.equals(offer.getUser().getId(), user.getId()) && offer.getUser().getFcmToken() != null) {
            Notification notification = new Notification(
                    offer.getCargo().getFromAddressUz() + " - " + offer.getCargo().getToAddressUz(),
                    offer.getCargo().getFromAddressRu() + " - " + offer.getCargo().getToAddressRu(),
                    offer.getCargo().getFromAddressBg() + " - " + offer.getCargo().getToAddressBg(),
                    "Yangi xabar keldi",
                    "Пришло новое сообщение",
                    "Янги хабар келди");

            Runnable runnable = () -> sendNotification(notification, offer.getUser().getFcmToken());
            Thread thread = new Thread(runnable);
            thread.start();
        } else if (!Objects.equals(offer.getCargo().getUser().getId(), user.getId())
                && offer.getCargo().getUser().getFcmToken() != null) {
            Notification notification = new Notification(
                    offer.getCargo().getFromAddressUz() + " - " + offer.getCargo().getToAddressUz(),
                    offer.getCargo().getFromAddressRu() + " - " + offer.getCargo().getToAddressRu(),
                    offer.getCargo().getFromAddressBg() + " - " + offer.getCargo().getToAddressBg(),
                    "Yangi xabar keldi",
                    "Пришло новое сообщение",
                    "Янги хабар келди");

            Runnable runnable = () -> sendNotification(notification, offer.getCargo().getUser().getFcmToken());
            Thread thread = new Thread(runnable);
            thread.start();
        }

        return repo.save(chatMessage);
    }

    @Override
    public ChatMessage saveChatFileMessage(
            Long offerId,
            Long cargoId,
            MultipartFile file
    ) {
        String username = BaseAppUtils.getCurrentUsername();
        User user = userRepo.findByUsername(username);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setOfferId(offerId);
        chatMessage.setCargoId(cargoId);
        chatMessage.setUser(user);

        String fileHashId = fileStorageService.save(file);

        chatMessage.setMessage(fileHashId);
        chatMessage.setMessageType(2);

        Offer offer = offerRepo.getReferenceById(offerId);
        if (!Objects.equals(offer.getUser().getId(), user.getId()) && offer.getUser().getFcmToken() != null) {
            Notification notification = new Notification(
                    offer.getCargo().getFromAddressUz() + " - " + offer.getCargo().getToAddressUz(),
                    offer.getCargo().getFromAddressRu() + " - " + offer.getCargo().getToAddressRu(),
                    offer.getCargo().getFromAddressBg() + " - " + offer.getCargo().getToAddressBg(),
                    "Yangi xabar keldi",
                    "Пришло новое сообщение",
                    "Янги хабар келди");

            Runnable runnable = () -> sendNotification(notification, offer.getUser().getFcmToken());
            Thread thread = new Thread(runnable);
            thread.start();
        } else if (!Objects.equals(offer.getCargo().getUser().getId(), user.getId())
                && offer.getCargo().getUser().getFcmToken() != null) {
            Notification notification = new Notification(
                    offer.getCargo().getFromAddressUz() + " - " + offer.getCargo().getToAddressUz(),
                    offer.getCargo().getFromAddressRu() + " - " + offer.getCargo().getToAddressRu(),
                    offer.getCargo().getFromAddressBg() + " - " + offer.getCargo().getToAddressBg(),
                    "Yangi xabar keldi",
                    "Пришло новое сообщение",
                    "Янги хабар келди");

            Runnable runnable = () -> sendNotification(notification, offer.getCargo().getUser().getFcmToken());
            Thread thread = new Thread(runnable);
            thread.start();
        }

        return repo.save(chatMessage);
    }

    @Override
    public Page<ChatMessage> getChatMessages(
            Integer page,
            Integer size,
            Long offerId,
            Long cargoId
    ) {

        BaseSpec<ChatMessage> spec1 = new BaseSpec<>(new SearchCriteria("id", ">", -1));
        Specification<ChatMessage> spec = Specification.where(spec1);

        if (offerId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("offerId", "=", offerId)));
        if (cargoId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("cargoId", "=", cargoId)));


        if (page == null) page = 0;
        if (size == null) size = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return repo.findAll(spec, pageable);
    }

    @Override
    public List<ChatMessage> getNewChatMessages(Long lastReadId, Long offerId, Long cargoId) {

        BaseSpec<ChatMessage> spec1 = new BaseSpec<>(new SearchCriteria("id", ">", lastReadId));
        Specification<ChatMessage> spec = Specification.where(spec1);

        if (offerId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("offerId", "=", offerId)));
        if (cargoId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("cargoId", "=", cargoId)));

        return repo.findAll(spec, Sort.by("id").descending());
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
}
