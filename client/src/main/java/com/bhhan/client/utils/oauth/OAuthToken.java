package com.bhhan.client.utils.oauth;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hbh5274@gmail.com on 2020-08-27
 * Github : http://github.com/bhhan5274
 */

@Getter
@Setter
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String jti;
    private long expires_in;
    private String scope;
}
