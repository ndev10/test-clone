package com.secured.jwt;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

/**
 * Converter class added to copy the map of JWT payload to the Authentication object.
 */
public class JWTConverter extends DefaultAccessTokenConverter {
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication auth = super.extractAuthentication(map);
        auth.setDetails(map); //this will get spring to copy JWT content into Authentication
        return auth;
    }
}
