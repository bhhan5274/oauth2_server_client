package com.bhhan.oauth2_server.web.error;

import lombok.Getter;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Getter
public enum ErrorCode {
    BAD_REQUEST("BAD_REQUEST_ERROR", "401");

    private String message;
    private String code;

    ErrorCode(String message, String code){
        this.message = message;
        this.code = code;
    }
}
