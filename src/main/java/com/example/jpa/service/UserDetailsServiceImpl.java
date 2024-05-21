package com.example.jpa.service;

import com.example.jpa.entity.CustomMember;
import com.example.jpa.entity.Member;
import com.example.jpa.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private MemberRespository memberRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("---loadUserByUsername---");
        Optional<Member> optional = memberRespository.findByUsername(username);
        if( !optional.isPresent()){
            throw new UsernameNotFoundException("user not found");
        }
        Member member = optional.get(); //회원정보 get
        return new CustomMember(member);//id  있으면 그 회원의 정보를 리턴해 패스워드 비교
        //UserDetails(Interface) --> User ( class, username, password,권한정보)
        //이게 세션으로 드러감

    }
}
