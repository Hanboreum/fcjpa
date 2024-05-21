package com.example.jpa.repository;

import com.example.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // role 정보 가져오기
    public Role findByName(String name);
}
