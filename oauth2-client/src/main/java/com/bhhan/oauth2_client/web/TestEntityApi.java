package com.bhhan.oauth2_client.web;

import com.bhhan.oauth2_client.domain.TestEntity;
import com.bhhan.oauth2_client.domain.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2020-08-27
 * Github : http://github.com/bhhan5274
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TestEntityApi {
    private final TestEntityRepository testEntityRepository;

    @GetMapping("/tests")
    public List<TestEntity> testEntities(){
        return testEntityRepository.findAll();
    }
}
