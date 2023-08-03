package uz.bprodevelopment.logisticsapp.base.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorMessages;
import uz.bprodevelopment.logisticsapp.base.entity.ErrorResponse;
import uz.bprodevelopment.logisticsapp.base.entity.User;
import uz.bprodevelopment.logisticsapp.base.service.UserService;
import uz.bprodevelopment.logisticsapp.base.util.BaseAppUtils;
import uz.bprodevelopment.logisticsapp.dto.UserDto;

import javax.transaction.Transactional;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.ACCOUNT_URL;
import static uz.bprodevelopment.logisticsapp.base.config.Urls.SIGNUP_URL;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @PostMapping(SIGNUP_URL)
    public ResponseEntity<?> signup(@RequestBody UserDto item){

        User user = userService.getOneByUsername(item.getUsername());
        if (user != null) {
            item.setId(user.getId());
            userService.save(item);
            User newUser = userService.getOneByUsername(item.getUsername());
            return ResponseEntity.ok().body(newUser);
        }else {
            userService.save(item);
            User newUser = userService.getOneByUsername(item.getUsername());
            return ResponseEntity.ok().body(newUser);
        }

    }

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

    @PostMapping(ACCOUNT_URL + "/my-debt")
    public ResponseEntity<Integer> myDebt(){
        User user = userService.getOneByUsername(BaseAppUtils.getCurrentUsername());
        return ResponseEntity.ok().body(user.getDebt() != null ? user.getDebt() : 0);
    }
}

