package com.bhhan.oauth2_server.config;

import com.bhhan.oauth2_server.domain.Account;
import com.bhhan.oauth2_server.domain.Role;
import com.bhhan.oauth2_server.service.AccountService;
import com.bhhan.oauth2_server.service.RoleService;
import com.bhhan.oauth2_server.service.dto.AccountDto;
import com.bhhan.oauth2_server.service.dto.ClientDetailsDto;
import com.bhhan.oauth2_server.service.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Sets;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Configuration
@RequiredArgsConstructor
public class InitConfig {
    private final AccountService accountService;
    private final RoleService roleService;

    @Bean
    public CommandLineRunner initData(){
        return args -> {
            final Role role1 = roleService.addRole(RoleDto.RoleReq.builder().name("ADMIN").build());
            final Role role2 = roleService.addRole(RoleDto.RoleReq.builder().name("USER").build());

            final Account account = accountService.addAccount(AccountDto.AccountReq.builder()
                    .name("testUser")
                    .email("test@email.com")
                    .password("12345678")
                    .roleIds(Arrays.asList(role1.getId(), role2.getId()))
                    .build());

            final ClientDetailsDto clientDetailsDto = ClientDetailsDto.builder()
                    .clientId("a9e0318d-13c7-4036-8d29-abc2adbb4917")
                    .clientSecret("de5038aa-c0b7-4e32-ac02-c18ad841f0b3")
                    .webServerRedirectUri(Sets.newLinkedHashSet("http://localhost:8082/login/oauth2/code/bhhan"))
                    .authorizedGrantTypes(Sets.newLinkedHashSet("authorization_code", "password"))
                    .scope(Sets.newLinkedHashSet("name", "email", "read"))
                    .build();

            accountService.addClientDetailsWithClientIdAndClientSecret(account.getId(), clientDetailsDto);
        };
    }
}
