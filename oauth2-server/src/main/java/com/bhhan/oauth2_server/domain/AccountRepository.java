package com.bhhan.oauth2_server.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}
