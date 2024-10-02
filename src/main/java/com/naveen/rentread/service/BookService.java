package com.naveen.rentread.service;

import com.naveen.rentread.domain.Book;
import com.naveen.rentread.model.BookCreationRequest;
import com.naveen.rentread.repos.BooksRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BooksRepository booksRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public Book createBook(BookCreationRequest creationRequest){
        Book bookEntity = modelMapper.map(creationRequest, Book.class);
        bookEntity.setAvailable(true);

        return booksRepository.save(bookEntity);
    }

    public void deleteBook(Long id){
        Optional<Book> book = booksRepository.findById(id);
        if(book.isPresent() && book.get().isAvailable()){
            booksRepository.deleteById(id);
        }
    }

    public List<Book> getAllBooks(){
        return booksRepository.findAll();
    }

    public List<Book> getAvailableBooks(){
        return booksRepository.findByAvailableTrue();
    }
}
