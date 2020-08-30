package com.bhhan.client.utils.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2020-08-30
 * Github : http://github.com/bhhan5274
 */

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(RestClientProperties restClientProperties){
        final RestTemplate restTemplate = new RestTemplate();

        final List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new ClientRequestInterceptor(restClientProperties));

        return restTemplate;
    }
}
