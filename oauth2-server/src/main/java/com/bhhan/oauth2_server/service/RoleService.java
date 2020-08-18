package com.bhhan.oauth2_server.service;

import com.bhhan.oauth2_server.domain.Role;
import com.bhhan.oauth2_server.domain.RoleRepository;
import com.bhhan.oauth2_server.service.dto.RoleDto;
import com.bhhan.oauth2_server.service.exception.RoleException;
import com.bhhan.oauth2_server.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role addRole(RoleDto.RoleReq roleReq){
        try{
            return roleRepository.save(roleMapper.roleReqToRole(roleReq));
        }catch(Exception e){
            throw new RoleException("Role 추가에 실패했습니다.");
        }
    }

    public void deleteRole(Long roleId){
        try{
            roleRepository.deleteById(roleId);
        }catch(Exception e){
            throw new RoleException("Role 삭제에 실패했습니다.");
        }
    }
}
