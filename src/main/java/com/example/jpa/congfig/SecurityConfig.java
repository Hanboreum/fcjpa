package com.example.jpa.congfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //안해줘도 boot가 해준다
public class SecurityConfig {

    //1.비밀번호 암호화 빈 설정
    @Bean //객체 생성 명령 어노테이션
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //회원 가입시 -> pw 암호화, db에 insert 하기 전
    }
    //2. security filter chain 객체 생성-> 인증 매니저(관리) -> userDetailsService(DB연동 서비스 클래스) -> 구현체
    //http://localhost:8080/ui/~
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{ //요청을 받는 객체
        //HttpSecurity -> 설정 (http에 설정)
        //1. 인증, 권한 설정 - url에 대해?
        http.authorizeHttpRequests(authz->authz
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/book/**").authenticated()
               //.requestMatchers("/ui/**").permitAll()//ui 아래 모든 경로는 인증 없이 접근 허용
                //.requestMatchers("/admin/**").hasRole("ADMIN") //ADMIN만 접근 가능
               // .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .anyRequest().permitAll()
        )
        //http.authorizeHttpRequests(authz ->authz.); //url을 받는다
        //2. 로그인 폼 - 커스텀 @Controller 에서
                .formLogin(form -> form
                        .loginPage("/ui/list") //form action ="/login 로그인 할 경로
                        .loginProcessingUrl("/login")// 이 서버로 오면 sc username, password라는 값 가져감
                        //usernamepasswordauthenticationfilter 동작
                        .defaultSuccessUrl("/ui/list", true) //성공 후 갈 url
                )

        //3. 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/logout") //이 url 로 넘어오면 sc가 로그아웃 처리)
                        //<form action ="/logout method = "post">,<a href="/logout">logout</a>
                        .logoutSuccessUrl("/ut/list")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //get방식으<a>로 로그아웃 할 때
                        .clearAuthentication(true) //securityContextHolder(session) 에 사용자 정보를 보관
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

}
