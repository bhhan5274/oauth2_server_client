package com.bhhan.oauth2_client.config;

import com.bhhan.oauth2_client.domain.TestEntity;
import com.bhhan.oauth2_client.domain.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

/**
 * Created by hbh5274@gmail.com on 2020-08-27
 * Github : http://github.com/bhhan5274
 */

@Component
@RequiredArgsConstructor
public class InitConfig implements ApplicationRunner {

    private final TestEntityRepository testEntityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IntStream.rangeClosed(1, 10)
                .forEach(i -> testEntityRepository.save(makeTestEntity(i)));
    }

    private TestEntity makeTestEntity(int i) {
        return TestEntity.builder().value(i + " ").build();
    }
}
