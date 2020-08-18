package com.bhhan.oauth2_server.service.mapper;

import com.bhhan.oauth2_server.domain.Role;
import com.bhhan.oauth2_server.service.dto.RoleDto;
import org.springframework.stereotype.Component;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Component
public class RoleMapper {
    public Role roleReqToRole(RoleDto.RoleReq roleReq){
        return Role.builder()
                .name(roleReq.getName())
                .build();
    }
}
