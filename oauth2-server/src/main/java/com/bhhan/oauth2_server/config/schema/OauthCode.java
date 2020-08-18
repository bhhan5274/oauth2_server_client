package com.bhhan.oauth2_server.config.schema;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.sql.Blob;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Entity
@Table(name = "OAUTH_CODE")
public class OauthCode {
    @Id
    private String code;

    @Lob
    private Blob authentication;
}
