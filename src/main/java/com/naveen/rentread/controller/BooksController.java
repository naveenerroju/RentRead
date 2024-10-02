package com.naveen.rentread.controller;

import com.naveen.rentread.domain.Book;
import com.naveen.rentread.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    private BookService service;

    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks(){
        return ResponseEntity.ok(service.getAvailableBooks());
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(service.getAllBooks());
    }
}
