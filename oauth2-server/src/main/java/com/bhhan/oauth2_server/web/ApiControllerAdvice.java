package com.bhhan.oauth2_server.web;

import com.bhhan.oauth2_server.web.error.ErrorCode;
import com.bhhan.oauth2_server.web.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ErrorResponse.builder()
                .errorCode(ErrorCode.BAD_REQUEST)
                .fieldErrors(e.getBindingResult().getFieldErrors())
                .build();
    }
}
