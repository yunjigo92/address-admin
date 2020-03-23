package com.admin.address.config.auth;

import com.admin.address.config.auth.dto.OAuthAttributes;
import com.admin.address.config.auth.dto.SessionUser;
import com.admin.address.domain.user.User;
import com.admin.address.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 중인 서비스를 구분하는 코드!! 구글,네이버,카카오 등등
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAtuth2 로그인시 키가 되는 것, pk와 같은 의미이다.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                        .getUserNameAttributeName();

        //OAuth2UserService를 사용해서 가져온 정보를 담은 클래스이다.
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName,oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //세션에 사용자 정보를 저장하기 위한 dto클래스 생성
        //왜 세션으로 했느냐?! 그냥 User클래스를 사용할경우 직렬화 오류가 발생하기 때문이다.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),
                                            attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
