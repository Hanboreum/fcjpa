package com.example.jpa.service;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.Role;
import com.example.jpa.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRespository memberRespository;
    private BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    //회원가입 메서드 (패스워드 암호화, 권한 부여)
    public Member register(Member member){ //member로 받고 넘겨줌
        //1. 암호화
        String hashedPwd = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPwd);

        //2.권한 부여
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByRole("USER");
        roles.add(userRole);
        member.setRoles(roles);

        return memberRespository.save(member);// 가입과
    }
}
