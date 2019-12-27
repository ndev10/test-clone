package com.oauth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("oauth.angular")
@Getter
@Setter
public class OAuth2Config {
    private String clientId;
    private String clientSecret;
    private String[] authorizedGrantTypes;
    private String[] scopes;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
}
