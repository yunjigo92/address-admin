package com.admin.address.config.auth;

import com.admin.address.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .headers().frameOptions().disable()//h2-console을 사용하기 위해 해당옵션을 disable한다.
                .and()
                    .authorizeRequests()// url별 권한관리를 위한 옵션의 설정
                    .antMatchers("/","/css/**","/js/**","/h2-console/**","/vendor/**","/img/**").permitAll()//해당 url들은 모든 권한을 줌
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())//해당 url은 Guest도 가능!
                    .anyRequest().authenticated()//나머지는 인증된 사람들이 사용하게함 즉, 로그인된 사람들!!
                .and()
                    .logout()//로그아웃 진입시
                        .logoutSuccessUrl("/")//성공하면 요청 url
                .and()
                    .oauth2Login()
                            .userInfoEndpoint()//로그인 성공 후 사용자 정보를 가져올때 사용
                                .userService(customOAuth2UserService);//가져온 데이터를 토대로 추가 액션 가능

    }

}
