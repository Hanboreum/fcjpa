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

    private final MemberRespository memberRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("---loadUserByUsername---");
        Optional<Member> optional = memberRespository.findByUsername(username);
        if( !optional.isPresent()){
            throw new UsernameNotFoundException("user not found");
        }
        Member member = optional.get(); //username에 해당하는 사용자 정보를 가져옴. 회원정보 get
        //HttpSession -> Security contextHolder
        return new CustomMember(member);//password 체크 . id  있으면 그 회원의 정보를 리턴해 패스워드 비교
        // 패스워드 비교해 security context holder 에 저장
        //UserDetails(Interface) --> User ( class, username, password,권한정보)
        //이게 세션으로 들어감

    }//CustomMember 와 Member 관계
}
