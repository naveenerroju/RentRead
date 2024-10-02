package com.naveen.rentread.service;

import com.naveen.rentread.repos.BooksRepository;
import com.naveen.rentread.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BooksRepository booksRepository;

    public void rentBook(String email, Long bookId){

    }

    public void returnBook(String email, Long bookId){

    }
}
