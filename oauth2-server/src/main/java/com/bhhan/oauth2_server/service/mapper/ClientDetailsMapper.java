package com.bhhan.oauth2_server.service.mapper;

import com.bhhan.oauth2_server.domain.ClientDetails;
import com.bhhan.oauth2_server.service.dto.ClientDetailsDto;
import org.springframework.stereotype.Component;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Component
public class ClientDetailsMapper {
    public ClientDetails clientDetailsReqToClientDetails(ClientDetailsDto clientDetailsDto){
        return new ClientDetails.ClientDetailsBuilder()
                .clientId(clientDetailsDto.getClientId())
                .clientSecret(clientDetailsDto.getClientSecret())
                .resourceIds(clientDetailsDto.getResourceIds())
                .scope(clientDetailsDto.getScope())
                .authorizedGrantTypes(clientDetailsDto.getAuthorizedGrantTypes())
                .webServerRedirectUri(clientDetailsDto.getWebServerRedirectUri())
                .authorities(clientDetailsDto.getAuthorities())
                .accessTokenValidity(clientDetailsDto.getAccessTokenValidity())
                .refreshTokenValidity(clientDetailsDto.getRefreshTokenValidity())
                .additionalInformation(clientDetailsDto.getAdditionalInformation())
                .autoApprove(clientDetailsDto.getAutoApprove())
                .build();
    }
}
