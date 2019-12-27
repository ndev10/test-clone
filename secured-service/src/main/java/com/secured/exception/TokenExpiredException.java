package com.secured.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class TokenExpiredException extends OAuth2Exception {

    public TokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenExpiredException(String msg) {
        super(msg);
    }

    @Override
    public int getHttpErrorCode() {
        return 401;
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "token_expired";
    }
}
