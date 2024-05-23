package com.example.jpa.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//session 에 custom member 들어감
public class CustomMember extends User {//userDetails -> User(username, pw, 권한정보authorities)


    private Member member;//이름, 번호, 주소 등 기타 정보가 Member에
    //String username, String password, Collection<? extends GrantedAuthority> authorities

    public CustomMember(Member member) { //Set<Role< --> Collection <GrantedAuthority> 변환 시켜야
        super(member.getUsername(), member.getPassword(), getToAuthorities(member.getRoles())); //USER -> [ROLE_USER]
        this.member=member;
    }
    //Set<Role< --> Collection <GrantedAuthority>

    private static Collection<? extends GrantedAuthority> getToAuthorities(Set<Role> roles){
        //stream=형변환 해줄때
        //USER(role), MANAGER(role -> ROLE_USER ,[ROLE_MANAGER] 로 바꿔야 함
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
