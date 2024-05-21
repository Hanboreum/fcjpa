package com.example.jpa.controller;

import com.example.jpa.entity.Book;
import com.example.jpa.entity.Member;
import com.example.jpa.service.BookService;
import com.example.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//뷰 접근
@Controller
@RequiredArgsConstructor
public class RouteController {

    private final BookService bookService;
    private final MemberService memberService;

    @GetMapping("/ui/list") //http://localhost:8080/ui/list
    public String bookList(Model model){
        List<Book> list = bookService.getList();
        model.addAttribute("list", list);
        return "book/list"; //list.html 타임리프 사용
    }

    @GetMapping("/member/register")
    public String register(){
        return "member/register"; //회원가입 페이지
    }

    @PostMapping("/member/register")
    public String register(Member member){ //파라미터수집
        memberService.register(member);
        return "redirect:/ui/list";
    }
}
