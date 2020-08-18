package com.bhhan.oauth2_server.web;

import com.bhhan.oauth2_server.service.AccountService;
import com.bhhan.oauth2_server.service.dto.AccountDto;
import com.bhhan.oauth2_server.service.dto.ClientDetailsDto;
import com.bhhan.oauth2_server.service.exception.AccountException;
import com.bhhan.oauth2_server.web.error.ErrorCode;
import com.bhhan.oauth2_server.web.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/account")
public class AccountApi {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccount(@Valid @RequestBody AccountDto.AccountReq accountReq){
        accountService.addAccount(accountReq);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
    }

    @PostMapping("/{accountId}/clientDetails")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto.AccountClientDetailsRes addClientDetails(@PathVariable Long accountId, @Valid @RequestBody ClientDetailsDto clientDetailsDto){
        return accountService.addClientDetails(accountId, clientDetailsDto);
    }

    @DeleteMapping("/{accountId}/clientDetails/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClientDetails(@PathVariable Long accountId, @PathVariable String clientId){
        accountService.deleteClientDetails(accountId, clientId);
    }

    @ExceptionHandler(value = AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountException(AccountException e){
        return ErrorResponse.builder()
                .errorCode(ErrorCode.BAD_REQUEST)
                .build();
    }
}
