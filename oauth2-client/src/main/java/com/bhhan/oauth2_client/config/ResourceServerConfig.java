package com.bhhan.oauth2_client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by hbh5274@gmail.com on 2020-08-19
 * Github : http://github.com/bhhan5274
 */

@Configuration
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final OAuth2Properties oAuth2Properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/update").access("#oauth2.hasScope('update')")
                .antMatchers("/delete").access("#oauth2.hasScope('delete')")
                .anyRequest()
                .authenticated()
            .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable();
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(oAuth2Properties.getJwtSigningKey());
        return converter;
    }
}
