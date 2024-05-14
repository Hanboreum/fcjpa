package com.example.jpa.service;

import com.example.jpa.entity.Book;
import com.example.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    //안써도 된다. db에서 가져온다 select = read only true
    public List<Book> getList() {
        //비즈니스 로직, 트랜잭션 처리

        //select * from book;
        return bookRepository.findAll();
    }

    public Book register(Book book) {
        return bookRepository.save(book);
    }
}
