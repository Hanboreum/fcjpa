package com.example.jpa.repository;

import com.example.jpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    //. jpa 사용 방법

    //1. jpa가 제공하는 것 사용
    //3. @Query("select * from book where []") , JPQL(JAva Persistence Query Language 사용자 정의 쿼리) 사용(Entity, table)
    //책 가격이 []만원 이상인 책을 검색
    @Query("select b from Book b where b.price > :price") //entity  기준 작성[파라미터 지정 = :price ]
    List<Book> getByPrice(@Param("price") int price);
    @Query(value = "select * from book b where b.price > ?1", nativeQuery = true)// table 기준
    List<Book> getByTitle(int price);

    //책 제목, 저자가 일치하는 sql - 1권 나온다면
    @Query("select b from Book b where b.author =:author and b.title = :title") //entity 기준
    Book getByTitleAndAuthor(@Param("author") String author,@Param("title") String title);
    @Query(value = "select * from Book b where b.title = ?1 and b.author = ?2", nativeQuery = true)//table
     Book getAuthorAndTitle(String author, String title);

    //4. QueryDsl - 고급

    //select * from book where name =[] ==findByName
    //find = select , By = where
    //미설정시 entity이름  기준으로
    //select b from Book b
    //findByName --> select b.title b.price from Book b where b.name = name

    //save == insert


    //2.쿼리메서드, 규칙을 가지고 만든다.
    //select * from where author=[]
    Optional<Book> findByAuthor(String author);
    Book findByAuthorAndPage(String author, int page);
    Book findByPriceOrPage(int price, int page);
    Book findByTitleAndAuthor(String title, String author);
}
