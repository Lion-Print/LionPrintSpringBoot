package uz.bprodevelopment.logisticsapp.base.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorMessages;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorResponse;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.service.UserService;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;

import javax.transaction.Transactional;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.ACCOUNT_URL;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;


    @GetMapping(ACCOUNT_URL)
    public ResponseEntity<?> userExist(
            @RequestParam(name = "lang", required = false) String lang,
            @RequestParam(name = "username") String username
    ){
        User dbUser = userService.getOneByUsername(username);
        if (dbUser == null){
            return ErrorResponse.getInstance().build(
                409,
                    "Username is not exist",
                    ErrorMessages.unregisteredPhone(lang),
                    ACCOUNT_URL
            );
        }
        return ResponseEntity.ok().body(dbUser);
    }

    @PostMapping(ACCOUNT_URL + "/{fcm}")
    @Transactional
    public ResponseEntity<?> fcm(
            @PathVariable(name = "fcm") String fcm
    ){
        User user = userService.getOneByUsername(BaseAppUtils.getCurrentUsername());
        user.setFcmToken(fcm);
        return ResponseEntity.ok().build();
    }

}

