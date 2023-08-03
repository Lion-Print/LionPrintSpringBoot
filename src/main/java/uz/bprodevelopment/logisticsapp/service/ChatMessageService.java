package uz.bprodevelopment.logisticsapp.service;


import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import uz.bprodevelopment.logisticsapp.entity.Cargo;
import uz.bprodevelopment.logisticsapp.entity.ChatMessage;

import java.util.List;


public interface ChatMessageService {

    Page<ChatMessage> getChatMessages(Integer page, Integer size, Long fromUserId, Long toUserId);

    List<ChatMessage> getNewChatMessages(Long lastReadId, Long offerId, Long cargoId);

    ChatMessage saveChatTextMessage(String message, Long offerId, Long cargoId);

    ChatMessage saveChatFileMessage(Long offerId, Long cargoId, MultipartFile file);

}
