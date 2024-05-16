package com.example.jpa.controller;

import com.example.jpa.entity.Book;
import com.example.jpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//뷰 접근
@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class RouteController {

    private final BookService bookService;

    @GetMapping("/list") //http://localhost:8080/ui/list
    public String bookList(Model model){
        List<Book> list = bookService.getList();
        model.addAttribute("list", list);
        return "book/list"; //list.html 타임리프 사용
    }
}
