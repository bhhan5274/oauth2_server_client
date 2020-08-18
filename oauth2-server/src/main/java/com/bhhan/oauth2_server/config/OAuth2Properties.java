package com.bhhan.oauth2_server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Configuration
@ConfigurationProperties(prefix = "oauth2")
@Validated
@Getter
@Setter
public class OAuth2Properties {
    @NotEmpty
    private String jwtSigningKey;
}
