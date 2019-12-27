package com.secured.exception;

import com.secured.exception.serializer.CustomOAuth2ExceptionJackson1Serializer;
import com.secured.exception.serializer.CustomOAuth2ExceptionJackson2Serializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.OAuth2ExceptionJackson1Deserializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2ExceptionJackson2Deserializer;

@org.codehaus.jackson.map.annotate.JsonSerialize(using = CustomOAuth2ExceptionJackson1Serializer.class)
@org.codehaus.jackson.map.annotate.JsonDeserialize(using = OAuth2ExceptionJackson1Deserializer.class)
@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = CustomOAuth2ExceptionJackson2Serializer.class)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = OAuth2ExceptionJackson2Deserializer.class)
public class CustomOauth2Exception extends OAuth2Exception {

    private static final long serialVersionUID = -4531148911960206664L;
    private String errorCode;

    public CustomOauth2Exception(String message, String errorCode) {
        super(message);
        this.errorCode= errorCode;
    }
    @Override
    public String getOAuth2ErrorCode() {
        return errorCode;
    }


}
