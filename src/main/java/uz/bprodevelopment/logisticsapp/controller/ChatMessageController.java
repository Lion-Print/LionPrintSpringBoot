package uz.bprodevelopment.logisticsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.bprodevelopment.logisticsapp.entity.ChatMessage;
import uz.bprodevelopment.logisticsapp.service.ChatMessageService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static uz.bprodevelopment.logisticsapp.utils.Urls.CARGO_URL;
import static uz.bprodevelopment.logisticsapp.utils.Urls.CHAT_MESSAGE_URL;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping(CHAT_MESSAGE_URL)
    public ResponseEntity<?> getChatMessages(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "offerId") Long offerId,
            @RequestParam(name = "cargoId") Long cargoId
            ) {
        return ResponseEntity.ok().body(
                chatMessageService.getChatMessages(page, size, offerId, cargoId)
        );
    }

    @GetMapping(CHAT_MESSAGE_URL + "/unread")
    public ResponseEntity<?> getNewChatMessages(
            @RequestParam(name = "lastReadId") Long lastReadId,
            @RequestParam(name = "offerId") Long offerId,
            @RequestParam(name = "cargoId") Long cargoId
            ) {
        return ResponseEntity.ok().body(
                chatMessageService.getNewChatMessages(lastReadId, offerId, cargoId)
        );
    }

    @PostMapping(path = CHAT_MESSAGE_URL)
    public ResponseEntity<?> saveChatMessage(
            @RequestParam String message,
            @RequestParam Long offerId,
            @RequestParam Long cargoId
    ) {
        ChatMessage chatMessage = chatMessageService.saveChatTextMessage(
                message, offerId, cargoId
        );
        return ResponseEntity.ok().body(chatMessage);
    }

    @PostMapping(path = CHAT_MESSAGE_URL + "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveChatMessageFile(
            @RequestParam Long offerId,
            @RequestParam Long cargoId,
            @RequestPart MultipartFile file
    ) {
        ChatMessage chatMessage = chatMessageService.saveChatFileMessage(
                offerId, cargoId, file
        );
        return ResponseEntity.ok().body(chatMessage);
    }

}