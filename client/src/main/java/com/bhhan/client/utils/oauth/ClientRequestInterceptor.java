package com.bhhan.client.utils.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;

/**
 * Created by hbh5274@gmail.com on 2020-08-30
 * Github : http://github.com/bhhan5274
 */
public class ClientRequestInterceptor implements ClientHttpRequestInterceptor {

    private RestClientProperties restClientProperties;
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public ClientRequestInterceptor(RestClientProperties restClientProperties) {
        this.restClientProperties = restClientProperties;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final OAuthToken oAuthToken = getOAuthToken();
        request.getHeaders()
                .add(HttpHeaders.AUTHORIZATION, "Bearer " + oAuthToken.getAccess_token());

        return execution.execute(request, body);
    }

    private OAuthToken getOAuthToken() throws com.fasterxml.jackson.core.JsonProcessingException {
        String encodedCredentials = getCredentials(restClientProperties.getClientId(), restClientProperties.getClientSecret());
        final HttpHeaders headers = makeHeader(encodedCredentials);
        final LinkedMultiValueMap<String, String> params = makeParams(restClientProperties);

        final HttpEntity<LinkedMultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        final ResponseEntity<String> response = restTemplate.postForEntity(restClientProperties.getAuthServerUrl(), requestEntity, String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            final String body = response.getBody();
            return objectMapper.readValue(body, OAuthToken.class);
        }

        return null;
    }

    private String getCredentials(@NotEmpty String clientId, @NotEmpty String clientSecret) {
        String credentials = String.format("%s:%s", clientId, clientSecret);
        return new String(Base64.encodeBase64(credentials.getBytes()));
    }

    private HttpHeaders makeHeader(String encodedCredentials) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);
        return headers;
    }

    private LinkedMultiValueMap<String, String> makeParams(RestClientProperties restClientProperties) {
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("redirect_uri", restClientProperties.getRedirect_uri());
        params.add("grant_type", restClientProperties.getGrant_type());
        params.add("username", restClientProperties.getUsername());
        params.add("password", restClientProperties.getPassword());
        params.add("scope", Strings.join(restClientProperties.getScopes(), ' '));
        return params;
    }
}
