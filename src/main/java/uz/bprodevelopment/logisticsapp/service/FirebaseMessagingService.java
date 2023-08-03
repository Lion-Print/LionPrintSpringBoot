package uz.bprodevelopment.logisticsapp.service;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface FirebaseMessagingService {

    void sendNotificationSingle(String title, String body, String token) throws FirebaseMessagingException;
    void sendNotificationMultiple(String title, String body, List<String> tokens) throws FirebaseMessagingException;

}