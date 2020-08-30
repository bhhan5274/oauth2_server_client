package com.bhhan.oauth2_client.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by hbh5274@gmail.com on 2020-08-27
 * Github : http://github.com/bhhan5274
 */

@Entity
@Table(name = "TestEntities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEST_ENTITY_ID")
    private Long id;

    private String value;

    @Builder
    public TestEntity(Long id, String value){
        this.id = id;
        this.value = value;
    }
}
