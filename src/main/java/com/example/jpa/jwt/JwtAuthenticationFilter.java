package com.example.jpa.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpa.entity.CustomMember;
import com.example.jpa.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //loginh -> JSON ->username.password
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(), Member.class);
            System.out.println(member);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            CustomMember customMember = (CustomMember) authentication.getPrincipal();
            System.out.println(customMember.getMember().getUsername());
            return authentication; //SecurityContextHolder (session)
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");
        //jwt token 만들어 응답
        CustomMember customMember = (CustomMember) authResult.getPrincipal();
        //jwt token =? header + payload + : jwt.io
        String jwtToken = JWT.create()
                .withSubject("JWT TOKEN")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000 * 10))) //토큰 만료 시간 10분
                .withClaim("username", customMember.getMember().getUsername())
                .sign(Algorithm.HMAC256("cosin"));

        //요청 클라이언트의 헤더에 응답해주기
        response.addHeader("Authorization","Bearer" + jwtToken);// 응답 확인

    }
}
