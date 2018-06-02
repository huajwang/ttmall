package com.longmaple.oauth2.config;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.longmaple.oauth2.jwe.JweTokenEnhancer;
import com.longmaple.oauth2.jwe.JweTokenSerializer;
import com.longmaple.oauth2.util.CustomClaimsTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private CustomClaimsTokenEnhancer tokenEnhancer;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder())
		.tokenKeyAccess("permitAll()");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain chain = new TokenEnhancerChain();     
		chain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jweTokenEnhancer()));

		endpoints.tokenStore(tokenStore()).tokenEnhancer(chain).accessTokenConverter(accessTokenConverter())
		.userDetailsService(userDetailsService).authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {    
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter(); 
		KeyStoreKeyFactory keyStoreKeyFactory = 
				new KeyStoreKeyFactory(new ClassPathResource("mykeypair.jks"), "123456".toCharArray());
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mykeypair"));
		return converter;
	}

	private JwtTokenStore tokenStore() {    
		return new JwtTokenStore(accessTokenConverter());

	}	
	
    @Bean
    public TokenEnhancer jweTokenEnhancer() {
        return new JweTokenEnhancer(accessTokenConverter(), new JweTokenSerializer(symmetricKey()));
    }
    
    @Bean
    public String symmetricKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

	@Bean    
	public ClientRegistrationService clientRegistrationService() {        
		return new JdbcClientDetailsService(dataSource);    	
	}
}
