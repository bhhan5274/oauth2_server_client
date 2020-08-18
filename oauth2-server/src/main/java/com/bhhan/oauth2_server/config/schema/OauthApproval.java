package com.bhhan.oauth2_server.config.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Entity
@Table(name = "OAUTH_APPROVALS")
public class OauthApproval {
    private String USERID;

    @Id
    private String CLIENTID;
    private String SCOPE;

    @Column(length = 10)
    private String STATUS;

    LocalDateTime EXPIRESAT;
    LocalDateTime LASTMODIFIEDAT;
}
