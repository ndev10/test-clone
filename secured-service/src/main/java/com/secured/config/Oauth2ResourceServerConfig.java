package com.secured.config;

import com.secured.exception.Oauth2WebResponseExceptionTranslator;
import com.secured.jwt.CustomTokenServices;
import com.secured.jwt.JWTConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private MessageSourceConfig messageSourceConfig;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(new JWTConverter());
        converter.setSigningKey(jwtSecret);
        return converter;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        List<String> files = messageSourceConfig.getFiles();
        if (CollectionUtils.isEmpty(files)) {
            source.setBasenames("error/common-error");
        } else {
            files.add("error/common-error");
            source.setBasenames(files.stream().toArray(String[] :: new));
        }

        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setExceptionTranslator(new Oauth2WebResponseExceptionTranslator());
        resources.authenticationEntryPoint(oAuth2AuthenticationEntryPoint);

        JwtTokenStore jwtTokenStore = jwtTokenStore();
        DefaultTokenServices tokenServices = new CustomTokenServices();
        tokenServices.setTokenStore(jwtTokenStore);
        tokenServices.setSupportRefreshToken(true);
        resources.tokenStore(jwtTokenStore).tokenServices(tokenServices);
    }
}
