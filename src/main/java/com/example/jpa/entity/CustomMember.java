package com.example.jpa.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

//session 에 custom member 들어감
public class CustomMember extends User {//userDetails -> User(username, pw, 권한정보authorities)


    private Member member;//이름, 번호, 주소 등 기타 정보가 Member에
    //String username, String password, Collection<? extends GrantedAuthority> authorities

    public CustomMember(Member member) { //Set<Role< --> Collection <GrantedAuthority> 변환 시켜야
        super(member.getUsername(), member.getPassword(), getAuthorities(member.getRoles())); //USER -> [ROLE_USER]
        this.member=member;
    }
    //Set<Role< --> Collection <GrantedAuthority>

   
}
