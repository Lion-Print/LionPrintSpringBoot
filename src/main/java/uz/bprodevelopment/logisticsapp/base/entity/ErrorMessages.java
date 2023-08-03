package uz.bprodevelopment.logisticsapp.base.entity;

import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorMessages {


    public static String userNotFound(String lang){
        switch (lang){
            case "bg": {
                return "Фойдаланувчи топилмади";
            }
            case "ru": {
                return "Пользователь не найден";
            }
            default: return "Foydalanuvchi topilmadi";
        }
    }

    public static String userIsNotDriver(String lang){
        switch (lang){
            case "bg": {
                return "Бу фойдаланувчи хайдовчи емас";
            }
            case "ru": {
                return "Это не пользовательский драйвер";
            }
            default: return "Bu foydalanuvchi xaydovchi emas";
        }
    }

    public static String requiredFieldIsNull(String lang){
        switch (lang){
            case "bg": {
                return "Мажбурий маълумот киритилмади";
            }
            case "ru": {
                return "Обязательная информация не включена";
            }
            default: return "Majburiy ma'lumot kiritilmadi";
        }
    }

    public static String unregisteredPhone(String lang){
        switch (lang){
            case "bg": {
                return "Бу рақам рўйҳатдан ўтмаган";
            }
            case "ru": {
                return "Этот номер не зарегистрирован";
            }
            default: return "Bu raqam ro'yhatdan o'tmagan";
        }
    }

    public static String registeredPhone(String lang){
        switch (lang){
            case "bg": {
                return "Бу рақам рўйҳатдан ўтган";
            }
            case "ru": {
                return "Этот номер зарегистрирован";
            }
            default: return "Bu raqam ro'yhatdan o'tgan";
        }
    }

    public static String offerLimit(String lang){
        switch (lang){
            case "bg": {
                return "Бошқа таклиф қабул қила олмайсиз. Етарлича қабул қилгансиз.";
            }
            case "ru": {
                return "Вы не можете принять другое предложение. Вы приняли достаточно.";
            }
            default: return "Boshqa taklif qabul qila olmaysiz. Yetarlicha qabul qilgansiz.";
        }
    }

    public static String receivingOfferFinished(String lang){
        switch (lang){
            case "bg": {
                return "Кечикдингиз. Таклиф қабул қилиш тугаган.";
            }
            case "ru": {
                return "Вы опоздали. Прием предложений завершен.";
            }
            default: return "Kechikdingiz. Taklif qabul qilish tugagan.";
        }
    }

    public static String offerNotNewStatus(String lang){
        switch (lang){
            case "bg": {
                return "Таклиф янги ҳолатда емас. Уни қабул қила олмайсиз.";
            }
            case "ru": {
                return "Предложение не в новом состоянии. Вы не можете принять это.";
            }
            default: return "Taklif yangi holatda emas. Uni qabul qila olmaysiz.";
        }
    }

    public static String youCanNotDelete(String lang){
        switch (lang){
            case "bg": {
                return "Bu yukda jarayondagi yoki yetkazilgan takliflar mavjud. Bunday yukni o'chirish mumkin emas.";
            }
            case "ru": {
                return "Это груз содержит предложения, сделанные в процессе или доставленные. Выключить такую груз невозможно.";
            }
            default: return "Бу юк жараёнда ёки етказиб берилган таклифларни ўз ичига олади. Бундай юкни ўчириб бўлмайди.";
        }
    }

}
