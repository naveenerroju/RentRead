package com.naveen.rentread.service;

import com.naveen.rentread.domain.Book;
import com.naveen.rentread.repos.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BooksRepository booksRepository;

    public List<Book> getAvailableBooks(){
        return booksRepository.findAll();
    }
}
