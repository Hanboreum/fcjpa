package com.example.jpa.service;

import com.example.jpa.entity.Book;
import com.example.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Book getById(Long id) {
         Optional<Book> optional =  bookRepository.findById(id); //id가 없다면

        if(optional.isPresent()){//존재시
            return optional.get();
        }else {
            throw new IllegalArgumentException("Book not found" + id) ;
        }
    }

    //수정
    @Transactional//중요
    public Book update(Long id, Book reqBook) {
        Optional<Book> optional = bookRepository.findById(id);//id로 책 읽어옴

        if(optional.isPresent()){
            Book book = optional.get(); //db에서 가져온 book 수정
            book.setTitle(reqBook.getTitle());//수정 동작
            book.setPrice(reqBook.getPrice());
           /* book.setPage(reqBook.getPage());
            book.setAuthor(reqBook.getAuthor());*/
            return book; //수정이 이루어짐
        } else{
            throw new IllegalArgumentException("Book not found" + id) ;
        }
    }
}
