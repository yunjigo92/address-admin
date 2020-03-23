package com.admin.address.config.auth.dto;

import com.admin.address.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    //해당 클래스는 인증된 사람만 사용하므로,
    //아래의 필요한 정보들만 세팅한다.

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
