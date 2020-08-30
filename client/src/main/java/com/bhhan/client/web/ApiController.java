package com.bhhan.client.web;

import com.bhhan.client.utils.oauth.RestClientProperties;
import com.bhhan.client.web.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2020-08-28
 * Github : http://github.com/bhhan5274
 */

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class ApiController {
    private final RestTemplate restTemplate;
    private final RestClientProperties restClientProperties;

    @GetMapping
    public List<TestDto> getTestEntities(){
        final ResponseEntity<List<TestDto>> responseEntity = restTemplate.exchange(restClientProperties.getResource_server_uri() + "/api/v1/tests", HttpMethod.GET, null, new ParameterizedTypeReference<List<TestDto>>() {
        });
        return responseEntity.getBody();
    }
}
