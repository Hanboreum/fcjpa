package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Book {

    @Id//PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Ai
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String title;
    private String author;
    private int price;
    private int page;
}
