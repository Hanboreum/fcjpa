package com.example.jpa.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpa.entity.CustomMember;
import com.example.jpa.entity.Member;
import com.example.jpa.repository.MemberRespository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    private MemberRespository memberRespository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,MemberRespository memberRespository) {
        super(authenticationManager);
        this.memberRespository = memberRespository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청이 되면 ");
        //jwt 토큰 읽어오기
        String jwtHeader = request.getHeader("Authorization");
        System.out.println(jwtHeader);
        if(jwtHeader == null || jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }
        //jwt token 검증
        String jwtToken = request.getHeader("Authorization").replace("Bearer","");
        String username =
                JWT.require(Algorithm.HMAC256("cosin"))
                        .build()
                        .verify(jwtToken)
                        .getClaim("username")
                        .asString();
        if(username != null){
            Optional<Member> optional = memberRespository.findByUsername(username);
            Member member = optional.get();
            CustomMember customMember = new CustomMember(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(customMember, null, customMember.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request,response);
        //super.doFilterInternal(request, response, chain);
    }
}
