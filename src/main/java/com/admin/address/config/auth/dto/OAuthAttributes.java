package com.admin.address.config.auth.dto;

import com.admin.address.domain.user.Role;
import com.admin.address.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes,
                           String nameAttributeKey,
                           String name,
                           String email,
                           String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }


    //OAuth2User에서 반환하는 정보는 Map이기 때문에 하나하나를 변환해야한다.
    public static OAuthAttributes of(String registationId,
                                     String userNameAttributeName,
                                     Map<String,Object> attributes){
                if("naver".equals(registationId)){
                    return ofNaver("id",attributes);
                }

        return ofGoogle(userNameAttributeName,attributes);
    }


    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName,Map<String,Object> attributes){
            Map<String,Object> map = (Map<String,Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String)map.get("name"))
                .email((String) map.get("email"))
                .picture((String) map.get("profile_image"))
                .attributes(map)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }



    //User entity를 생성한다.
    //엔티티를 생성하는 시점이 처음 가입할때이다.
    //이때 기본권한은 GEUST로 준것이다.
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
