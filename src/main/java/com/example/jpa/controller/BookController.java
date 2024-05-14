package com.example.jpa.controller;

import com.example.jpa.entity.Book;
import com.example.jpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;


//@Controller //뷰 사용할 때. view -> forward, redirect,@ResponseBody Json
@RestController //뷰 없이 할 때, React 사용시. rest는 뷰를 사용 못한다
@RequestMapping("/api") // <-- 외부 경로 CORS
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    //GET: http://localhost8080/api/book : OpenAPI <-- key발급(JWT)
    @GetMapping("/book")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(bookService.getList(), HttpStatus.OK);//200
    }

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book){ //json으로 받기
        return new ResponseEntity<>(bookService.register(book), HttpStatus.CREATED);//201
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(id,HttpStatus.NOT_FOUND);
        }
    }

    //수정
    @PutMapping("/book/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){
        try{
            Book b= bookService.update(id, book);
            return ResponseEntity.ok(b);
            //return new ResponseEntity<>(bookService.update(id, book),HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            bookService.getByDelete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
