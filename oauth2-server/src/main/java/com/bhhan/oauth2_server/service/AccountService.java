package com.bhhan.oauth2_server.service;

import com.bhhan.oauth2_server.domain.Account;
import com.bhhan.oauth2_server.domain.AccountRepository;
import com.bhhan.oauth2_server.domain.Role;
import com.bhhan.oauth2_server.domain.RoleRepository;
import com.bhhan.oauth2_server.service.dto.AccountDto;
import com.bhhan.oauth2_server.service.dto.ClientDetailsDto;
import com.bhhan.oauth2_server.service.exception.AccountException;
import com.bhhan.oauth2_server.service.mapper.ClientDetailsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientDetailsMapper clientDetailsMapper;

    public AccountDto.AccountInfo getAccountInfo(Long accountId){
        final Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException("Account를 찾을 수 없습니다."));

        return AccountDto.AccountInfo.builder()
                .account(account)
                .build();
    }

    public AccountDto.AccountInfo getAccountInfo(String email){
        final Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException("Account를 찾을 수 없습니다."));

        return AccountDto.AccountInfo.builder()
                .account(account)
                .build();
    }

    public AccountDto.AccountClientDetailsRes addClientDetails(Long accountId, ClientDetailsDto clientDetailsDto){

        try {
            final Account account = accountRepository.findById(accountId)
                    .orElseThrow(IllegalArgumentException::new);

            insertClientIdAndClientSecret(clientDetailsDto);
            account.addClientDetails(clientDetailsMapper.clientDetailsReqToClientDetails(clientDetailsDto));

            return AccountDto.AccountClientDetailsRes.builder()
                    .accountId(accountId)
                    .clientDetailsDto(clientDetailsDto)
                    .build();

        }catch (Exception e){
            throw new AccountException("Account ClientDetails 추가에 실패했습니다.");
        }
    }

    public AccountDto.AccountClientDetailsRes addClientDetailsWithClientIdAndClientSecret(Long accountId, ClientDetailsDto clientDetailsDto){

        try {
            final Account account = accountRepository.findById(accountId)
                    .orElseThrow(IllegalArgumentException::new);

            account.addClientDetails(clientDetailsMapper.clientDetailsReqToClientDetails(clientDetailsDto));

            return AccountDto.AccountClientDetailsRes.builder()
                    .accountId(accountId)
                    .clientDetailsDto(clientDetailsDto)
                    .build();

        }catch (Exception e){
            throw new AccountException("Account ClientDetails 추가에 실패했습니다.");
        }
    }

    public void deleteClientDetails(Long accountId, String clientId){
        try{
            final Account account = accountRepository.findById(accountId)
                    .orElseThrow(IllegalArgumentException::new);
            account.removeClientDetails(clientId);
        }catch(Exception e){
            throw new AccountException("Account ClientDetails 삭제에 실패했습니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new User(account.getEmail(), account.getPassword(), getGrantedAuthorities(account));
    }

    public Account addAccount(AccountDto.AccountReq accountReq){

        try{
            List<Role> roles = getAccountRoles(accountReq.getRoleIds());

            final Account account = Account.builder()
                    .email(accountReq.getEmail())
                    .name(accountReq.getName())
                    .password(encodingPassword(accountReq.getPassword()))
                    .roles(roles)
                    .build();

            return accountRepository.save(account);

        }catch(Exception e){
            throw new AccountException("Account 생성에 실패했습니다.");
        }
    }

    public void deleteAccount(Long accountId){
        try {
            accountRepository.deleteById(accountId);
        }catch(Exception e){
            throw new AccountException("Account 삭제에 실패했습니다.");
        }
    }

    private void insertClientIdAndClientSecret(ClientDetailsDto clientDetailsDto) {
        clientDetailsDto.setClientId(UUID.randomUUID().toString());
        clientDetailsDto.setClientSecret(UUID.randomUUID().toString());
    }

    private List<SimpleGrantedAuthority> getGrantedAuthorities(Account account) {
        return account.getRoles().stream().map((Role role) -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    private List<Role> getAccountRoles(List<Long> roleIds) {
        List<Role> roles = new ArrayList<>();

        for (Long roleId : roleIds) {
            roles.add(roleRepository.findById(roleId)
                    .orElseThrow(IllegalArgumentException::new));
        }

        return roles;
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
