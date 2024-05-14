package com.example.jpa.repository;

import com.example.jpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    //select * from book where name =[] ==findByName
    //find = select , By = where
    //미설정시 entity이름  기준으로
    //select b from Book b
    //findByName --> select b.title b.price from Book b where b.name = name

    //save == insert
}
