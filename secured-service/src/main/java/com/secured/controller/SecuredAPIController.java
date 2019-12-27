package com.secured.controller;


import com.secured.dto.UserInfoDTO;
import com.secured.util.AuthorizationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class SecuredAPIController {

    @GetMapping({"/user/info"})
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO getUserInfoFromToken() {
        return AuthorizationUtil.getUserInfoFromToken();
    }
}
