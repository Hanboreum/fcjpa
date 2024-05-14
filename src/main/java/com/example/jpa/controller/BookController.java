package com.example.jpa.controller;

import com.example.jpa.entity.Book;
import com.example.jpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@Controller //뷰 사용할 때. view -> forward, redirect,@ResponseBody Json
@RestController //뷰 없이 할 때, React 사용시
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    //GET: http://localhost8080/api/book
    @GetMapping("/book")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(bookService.getList(), HttpStatus.OK);//200
    }

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book){ //json으로 받기
        return new ResponseEntity<>(bookService.register(book), HttpStatus.CREATED);//201
    }
}
