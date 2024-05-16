package com.example.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true) //null X, unique
    private String username; //spring security , 아이디는 무조건 username
    private String password; //pw무조건 password. 암호화 해서 저장
    private String uname; //실명
    private String email;


    /*
    table member_roles
    [ member_id (fk),
    roles_id(fk) ]  : 이 두개를 묶어 pk
     */
    //권한 (한명이 여러개 가능) set = 중복불가
    @ManyToMany(fetch = FetchType.EAGER)// M : N ,  사용해 멤버 권한 정보 가져오기, LAZY : 권한 정보 가져오지 않음
    @JoinTable(//자동 생성, 두 테이블의 pk, jpa에서 자동으로 값 할당
            name = "member_roles",//table 명
            joinColumns = @JoinColumn(name = "member_id"), //fk의 이름
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles; //role - 1.USER, 2.MANAGER, 3.ADMIN

}

/*

한 명의 회원은 여러 개의 권한을 가질 수 있음. 다대다 관계
회원      권한: (1user, 2manager, 3admin, 4~~)
    M   :   N
 1. 나    : user, manager
 2. 관리자 : admin


 중간에 관계 테이블 필요 (어떤 회원이 어떤 권한을 가지고 있는지)
 member_roles table = 관계 테이블
    (fk     +       fk) : PK로 만들어야 함.
    1(회원)       1(user)
    1            2(manager)
    2             2(manager)
    2            3(admin)

    고객                제품
    1                   N
    N                   1

    1                   1 (우유)
    2                   2 (라면)
    3                   3 (콜라)
        구매(관계 테이블:행위 - 비즈니스 관계)
 PK       FK          FK        수량
 1        1            1 :우유      2
 2        2            3  ==2번 고객이 3콜라 구매
 3        1            1
 4        2            2


                학생       과목
                수강 (관계 테이블)
AK대체키        [FK         FK] : PK       일자
    1            1          1           2023-01-01
    2            1          2
    3            2          3
    4            3          3
  */