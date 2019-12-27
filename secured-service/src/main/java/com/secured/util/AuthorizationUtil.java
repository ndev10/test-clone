package com.secured.util;

import com.secured.dto.UserInfoDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

public class AuthorizationUtil {

    public static Map<String, Object> getTokenDetails(){
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return (Map<String, Object>) details.getDecodedDetails();
    }

    public static UserInfoDTO getUserInfoFromToken() {

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        Map<String, Object> map = getTokenDetails();

        userInfoDTO.setId(Long.parseLong(map.get("userId").toString()));
        userInfoDTO.setEmail(map.get("email").toString());
        userInfoDTO.setFirstName(map.get("firstName").toString());
        userInfoDTO.setLastName(map.get("lastName").toString());

        return userInfoDTO;

    }

}
