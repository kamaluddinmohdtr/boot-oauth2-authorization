package com.javainuse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
public class CustomTokenServices extends DefaultTokenServices {

    CustomTokenServices(TokenStore tokenStore,Boolean supportRefreshtoken)
    {
        this.setTokenStore(tokenStore);
        this.setSupportRefreshToken(true);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication)  {
        try {
            return super.createAccessToken(authentication);
        }catch (DuplicateKeyException dke) {
           return super.getAccessToken(authentication);
        }catch (Exception ex) {
        }
        return null;
    }
}
