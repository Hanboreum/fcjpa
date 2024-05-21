package com.example.jpa.repository;

import com.example.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRespository extends JpaRepository<Member, Long> {

    //회원가입 메서드 save()

    public Optional<Member> findByUsername(String username);
}
