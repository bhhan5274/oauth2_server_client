package com.bhhan.client.config.auth.dto;

import com.bhhan.client.domain.Role;
import com.bhhan.client.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * Created by hbh5274@gmail.com on 2020-08-27
 * Github : http://github.com/bhhan5274
 */

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Getter
    private enum CUSTOM_PROVIDER {
        NAVER("id", "response"),
        BHHAN("id", ""),
        KAKAO("id", "kakao_account");

        CUSTOM_PROVIDER(String attributeName, String rootAttributeName){
            this.attributeName = attributeName;
            this.rootAttributeName = rootAttributeName;
        }

        private String attributeName;
        private String rootAttributeName;
    }
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String clientName, String userNameAttributeName,
                                     Map<String, Object> attributes){
        if("naver".equalsIgnoreCase(clientName)){
            return ofNaver(CUSTOM_PROVIDER.NAVER.getAttributeName(), attributes);
        }else if("google".equalsIgnoreCase(clientName)){
            return ofGoogle(userNameAttributeName, attributes);
        }else if("bhhan".equalsIgnoreCase(clientName)){
            return ofBhhan(CUSTOM_PROVIDER.BHHAN.getAttributeName(), attributes);
        }else if("kakao".equalsIgnoreCase(clientName)){
            return ofKakao(CUSTOM_PROVIDER.KAKAO.getAttributeName(), attributes);
        }

        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        final Map<String, Object> response = (Map<String, Object>) attributes.get(CUSTOM_PROVIDER.NAVER.getRootAttributeName());

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){
        final Map<String, Object> account = (Map<String, Object>) attributes.get(CUSTOM_PROVIDER.KAKAO.getRootAttributeName());
        final Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .attributes(account)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofBhhan(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
