package uz.bprodevelopment.logisticsapp.base.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendNotificationSingle(String title, String body, String token) throws FirebaseMessagingException {

//        Notification notification = Notification
//                .builder()
//                .setTitle(title)
//                .setBody(body)
//                .build();

        Message message = Message
                .builder()
                .putData("data", body)
                .setToken(token)
//                .setNotification(notification)
                .build();
        firebaseMessaging.send(message);
    }

    @Override
    public void sendNotificationMultiple(String title, String body, List<String> tokens) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(body)
                .build();

        MulticastMessage message = MulticastMessage
                .builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .build();

        firebaseMessaging.sendMulticast(message);
        
    }
}