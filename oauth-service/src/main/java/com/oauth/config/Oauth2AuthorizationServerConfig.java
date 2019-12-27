package com.oauth.config;


import com.oauth.jwt.token.enhancer.CustomJWTAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private OAuth2Config config;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenStore tokenStore;

    public Oauth2AuthorizationServerConfig(OAuth2Config config) {
		this.config = config;
    }


    @Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.tokenStore(this.tokenStore)
				 .accessTokenConverter(this.jwtAccessTokenConverter)
				 //.exceptionTranslator(new Oauth2WebResponseExceptionTranslator())
				 .authenticationManager(this.authenticationManager)
				 .userDetailsService(userDetailsService);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(this.jwtAccessTokenConverter);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		CustomJWTAccessTokenConverter converter = new CustomJWTAccessTokenConverter();
		converter.setSigningKey(jwtSecret);
		return converter;
	}
	
	@Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(this.tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient(config.getClientId())
				.secret(new BCryptPasswordEncoder().encode(config.getClientSecret()))
				.authorizedGrantTypes(config.getAuthorizedGrantTypes())
				.scopes(config.getScopes())
				.accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
				.refreshTokenValiditySeconds(config.getRefreshTokenValiditySeconds());
	}

}
