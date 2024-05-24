package com.example.jpa.congfig;

import com.example.jpa.jwt.JwtAuthenticationFilter;
import com.example.jpa.jwt.JwtAuthorizationFilter;
import com.example.jpa.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //안해줘도 boot가 해준다
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRespository memberRespository;

    //1.비밀번호 암호화 빈 설정
    @Bean //객체 생성 명령 어노테이션
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //회원 가입시 -> pw 암호화, db에 insert 하기 전
    }
    //2. security filter chain 객체 생성-> 인증 매니저(관리) -> userDetailsService(DB연동 서비스 클래스) -> 구현체
    //http://localhost:8080/ui/~
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        /*
        //요청을 받는 객체
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
                        .logoutSuccessUrl("/ui/list")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //get방식으<a>로 로그아웃 할 때
                        .clearAuthentication(true) //securityContextHolder(session) 에 사용자 정보를 보관
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                );

        return http.build();

         */

        //폼 방식 아님
        http
                .formLogin(form->form.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세성 미사용
                .httpBasic(hb-> hb.disable())
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authz->authz        //new SecurityContentHolder (session)이 만들어져야
                        .requestMatchers("/jwt/v1/user/**").hasAnyRole("USER","MANAGER","ADMIN")
                        .requestMatchers("/jwt/v1/manager/**").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers("/jwt/v1/admin/**").hasAnyRole("ADMIN")
                )
                .apply(new MyCustomDsl());

        return http.build();

    }
    //인증에 필요한 filter 동작시키기 위해 사용자 정의 클래스 등록(DSL)
    //UserNamePasswordAuthenticationFilter 가 동작하지 못한다.
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity>{

        @Override
        public void configure(HttpSecurity http) throws Exception {
            //Authentication Manager 얻어오기
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            //UsernamePasswordAuthenticationManagerFilter 실행 할 수 있다.
            http
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRespository));

        }
    }
}
