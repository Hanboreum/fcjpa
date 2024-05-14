package com.example.jpa.repository;

import com.example.jpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    //. jpa 사용 방법

    //1. jpa가 제공하는 것 사용
    //3. @Query("select * from book where []") , JPQL 사용(Entity, table)
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
}
