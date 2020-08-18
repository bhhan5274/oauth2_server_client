package com.bhhan.oauth2_server.web.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String message;
    private String code;
    private List<FieldError> fieldErrors = new ArrayList<>();

    @Builder
    public ErrorResponse(ErrorCode errorCode, List<org.springframework.validation.FieldError> fieldErrors){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();

        if(Objects.nonNull(fieldErrors)){
            this.fieldErrors.addAll(fieldErrors.stream()
            .map(FieldError::new)
            .collect(Collectors.toList()));
        }
    }

    @Getter
    public static class FieldError {
        private final String code;
        private final String field;
        private final String rejectedValue;

        public FieldError(org.springframework.validation.FieldError fieldError){
            this.code = fieldError.getCode();
            this.field = fieldError.getField();
            final Object rejectedValue = fieldError.getRejectedValue();

            this.rejectedValue = rejectedValue == null ? "null" : rejectedValue.toString();
        }
    }
}
