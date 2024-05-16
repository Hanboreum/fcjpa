package com.example.jpa.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private Long id;
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