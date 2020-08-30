package com.bhhan.client.web.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by hbh5274@gmail.com on 2020-08-30
 * Github : http://github.com/bhhan5274
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestDto {
    private Long id;
    private String value;

    public TestDto(Long id, String value){
        this.id = id;
        this.value = value;
    }
}
