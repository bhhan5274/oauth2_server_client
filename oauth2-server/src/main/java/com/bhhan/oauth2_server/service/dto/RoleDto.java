package com.bhhan.oauth2_server.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */
public class RoleDto {
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RoleReq {
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9_-]{2,20}$")
        private String name;

        @Builder
        public RoleReq(String name){
            this.name = name;
        }
    }
}
