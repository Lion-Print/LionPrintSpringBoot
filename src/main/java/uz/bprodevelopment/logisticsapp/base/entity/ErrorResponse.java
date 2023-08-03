package uz.bprodevelopment.logisticsapp.base.entity;

import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse extends RuntimeException{

    private Date timestamp;
    private Integer status = 500;
    private String error = "Internal server error";
    private String message = "Something went wrong. Retry later";
    private String path = "";

    public static ErrorResponse response = null;

    public static ErrorResponse getInstance(){
        if (response == null){
            response = new ErrorResponse();
        }
        return response;
    }

    public ResponseEntity<?> build(Integer status, String error, String message, String path){
        response.timestamp = new Date();
        response.status = status;
        response.error = error;
        response.message = message;
        response.path = path;
        return ResponseEntity.status(status).body(response);
    }

    public ResponseEntity<?> build(String error, String message, String path){
        response.timestamp = new Date();
        response.error = error;
        response.message = message;
        response.path = path;
        return ResponseEntity.status(status).body(response);
    }

    public ResponseEntity<?> build(String error, String message){
        response.timestamp = new Date();
        response.error = error;
        response.message = message;
        return ResponseEntity.status(status).body(response);
    }

    public ResponseEntity<?> build(String message){
        response.timestamp = new Date();
        response.message = message;
        return ResponseEntity.status(status).body(response);
    }

    public Map<String, Object> buildMap(Integer status,String error, String message, String path){
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("error", error);
        map.put("message", message);
        map.put("path", path);
        return map;
    }
}
