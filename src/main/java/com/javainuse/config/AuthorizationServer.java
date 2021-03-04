package com.javainuse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer

public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

@Autowired
	TokenStore tokenStore;
	@Autowired
	DataSource dataSource;

	@Autowired
	CustomTokenServices tokenServices;
//CustomTokenServices tokenServices = new CustomTokenServices( tokenStore,true);
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.tokenStore(tokenStore)
				.tokenServices(tokenServices)
		//.reuseRefreshTokens(false)
				.authenticationManager(authenticationManager);
	}

	/*@Bean
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(tokenStore);
		return tokenServices;
	}*/


	@Bean
	@Primary
	public CustomTokenServices tokenServices() {
		 CustomTokenServices tokenServices = new CustomTokenServices(tokenStore,true);
		///tokenServices.setSupportRefreshToken(true);
		//tokenServices.setTokenStore(tokenStore);
		return tokenServices;
	}
	/*@Bean
	@Primary
	public DefaultTokenServices tokenServices() {

		return new CustomTokenServices(tokenStore,true);
	}*/
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("viewer").secret("viewer_password")
				.authorizedGrantTypes("client_credentials").scopes("resource-server-read", "resource-server-write");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				;
	}

}