package com.bhhan.oauth2_server.web;

import com.bhhan.oauth2_server.domain.*;
import com.bhhan.oauth2_server.service.RoleService;
import com.bhhan.oauth2_server.service.dto.AccountDto;
import com.bhhan.oauth2_server.service.dto.ClientDetailsDto;
import com.bhhan.oauth2_server.service.dto.RoleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
class AccountApiTest {
    private static final String CLIENT_ID = "clientId";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    private Role role;
    private Account account;

    @BeforeEach
    void init(){
        role = roleService.addRole(RoleDto.RoleReq.builder().name("COMMON").build());
        account = accountRepository.save(Account.builder().email("test@gmail.com").password("12345678").roles(Arrays.asList(role)).build());
        account.addClientDetails(new ClientDetails.ClientDetailsBuilder()
                .clientId(CLIENT_ID)
                .build());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("account_add_test")
    @Test
    void account_add_test() throws Exception {

        final AccountDto.AccountReq accountReq = AccountDto.AccountReq.builder()
                .email("test@example.com")
                .password("abcdef1234")
                .roleIds(Arrays.asList(role.getId()))
                .build();

        mockMvc.perform(post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountReq)))
                .andDo(print())
                .andExpect(status().isCreated());

        accountRepository.flush();
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("account_add_fail_test [잘못된 Email]")
    @Test
    void account_add_fail_test1() throws Exception {
        final AccountDto.AccountReq accountReq = AccountDto.AccountReq.builder()
                .email("testemail.com")
                .password("1234hello")
                .roleIds(Arrays.asList(role.getId()))
                .build();

        mockMvc.perform(post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountReq)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[0].code").exists())
                .andExpect(jsonPath("$.fieldErrors[0].field").exists())
                .andExpect(jsonPath("$.fieldErrors[0].rejectedValue").exists());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("account_add_fail_test [잘못된 Password]")
    @Test
    void account_add_fail_test2() throws Exception {
        final AccountDto.AccountReq accountReq = AccountDto.AccountReq.builder()
                .email("test@email.com")
                .password("hello(12345!")
                .roleIds(Arrays.asList(role.getId()))
                .build();

        mockMvc.perform(post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountReq)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[0].code").exists())
                .andExpect(jsonPath("$.fieldErrors[0].field").exists())
                .andExpect(jsonPath("$.fieldErrors[0].rejectedValue").exists());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("account_delete_test")
    @Test
    void account_delete_test() throws Exception {
        mockMvc.perform(delete("/v1/account/" + account.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        accountRepository.flush();
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("account_clientDetails_add_test")
    @Test
    void account_clientDetails_add_test() throws Exception {
        final HashMap<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("key1", "value1");
        additionalInformation.put("key2", "value2");

        final ClientDetailsDto clientDetailsDto = ClientDetailsDto.builder()
                .scope(Sets.newSet("scope1", "scope2"))
                .authorizedGrantTypes(Sets.newSet("authorization_code", "password"))
                .additionalInformation(additionalInformation)
                .build();

        mockMvc.perform(post("/v1/account/" + account.getId() + "/clientDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(clientDetailsDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("account_clientDetails_delete_test")
    @Test
    void account_clientDetails_delete_test() throws Exception {
        mockMvc.perform(delete("/v1/account/" + account.getId() + "/clientDetails/" + CLIENT_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }
}