package com.bhhan.client.utils.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2020-08-30
 * Github : http://github.com/bhhan5274
 */

@Component
@ConfigurationProperties(prefix = "bhhan")
@Validated
@Getter
@Setter
public class RestClientProperties {
    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String authServerUrl;

    @NotEmpty
    private String grant_type;

    @NotEmpty
    private String redirect_uri;

    @NotEmpty
    private String resource_server_uri;

    @Size(min = 1)
    private List<String> scopes;
}
