package com.bhhan.oauth2_server.service.dto;

import lombok.*;

import java.io.Serializable;
import java.util.*;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientDetailsDto implements Serializable {
    private String clientId = "";
    private String clientSecret = "";
    private Set<String> resourceIds = new HashSet<>();
    private Set<String> scope = new HashSet<>();
    private Set<String> authorizedGrantTypes = new HashSet<>();
    private Set<String> webServerRedirectUri = new HashSet<>();
    private Set<String> authorities = new HashSet<>();
    private Integer accessTokenValidity = 4200;
    private Integer refreshTokenValidity = 4200;
    private Map<String, Object> additionalInformation = new HashMap<>();
    private Set<String> autoApprove = new HashSet<>();

    @Builder
    public ClientDetailsDto(String clientId, String clientSecret, Set<String> resourceIds,
                            Set<String> scope, Set<String> authorizedGrantTypes, Set<String> webServerRedirectUri,
                            Set<String> authorities, Integer accessTokenValidity, Integer refreshTokenValidity,
                            Map<String, Object> additionalInformation, Set<String> autoApprove){
        if(Objects.nonNull(clientId)){
            this.clientId = clientId;
        }

        if(Objects.nonNull(clientSecret)){
            this.clientSecret = clientSecret;
        }

        if(Objects.nonNull(resourceIds)){
            this.resourceIds.addAll(resourceIds);
        }

        if(Objects.nonNull(scope)){
            this.scope.addAll(scope);
        }

        if(Objects.nonNull(authorizedGrantTypes)){
            this.authorizedGrantTypes.addAll(authorizedGrantTypes);
        }

        if(Objects.nonNull(webServerRedirectUri)){
            this.webServerRedirectUri.addAll(webServerRedirectUri);
        }

        if(Objects.nonNull(authorities)){
            this.authorities.addAll(authorities);
        }

        if(Objects.nonNull(accessTokenValidity)){
            this.accessTokenValidity = accessTokenValidity;
        }

        if(Objects.nonNull(refreshTokenValidity)){
            this.refreshTokenValidity = refreshTokenValidity;
        }

        if(Objects.nonNull(additionalInformation)){
            this.additionalInformation.putAll(additionalInformation);
        }

        if(Objects.nonNull(autoApprove)){
            this.autoApprove.addAll(autoApprove);
        }
    }
}
