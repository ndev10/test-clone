package com.oauth.jwt.token.enhancer;


import com.oauth.dto.UserDetailsDTO;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class CustomJWTAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalJWTInfo = new HashMap<>();
        UserDetailsDTO userDetailsDto = (UserDetailsDTO) authentication.getPrincipal();
        additionalJWTInfo.put("email", userDetailsDto.getEmail());
        additionalJWTInfo.put("userId", userDetailsDto.getId());
        additionalJWTInfo.put("firstName", userDetailsDto.getFirstName());
        additionalJWTInfo.put("lastName", userDetailsDto.getLastName());


        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalJWTInfo);
        accessToken = super.enhance(accessToken, authentication);
        Map<String, Object> map = new HashMap<>();
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
        return accessToken;
    }

}
