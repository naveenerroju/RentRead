package com.naveen.rentread.service;

import com.naveen.rentread.domain.Book;
import com.naveen.rentread.model.BookCreationRequest;
import com.naveen.rentread.repos.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BookService bookService;

    private ModelMapper modelMapper = new ModelMapper();

    private BookCreationRequest bookCreationRequest;
    private Book book;

    @BeforeEach
    public void setUp() {
        bookCreationRequest = new BookCreationRequest();
        bookCreationRequest.setTitle("Test Book");
        bookCreationRequest.setAuthor("Test Author");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setAvailable(true);
    }

    @Test
    public void testCreateBook() {
        when(booksRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(bookCreationRequest);

        assertNotNull(createdBook);
        assertEquals(book.getTitle(), createdBook.getTitle());
        assertEquals(book.getAuthor(), createdBook.getAuthor());
        assertTrue(createdBook.isAvailable());
        verify(booksRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testDeleteBook() {
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(booksRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteBook_NotAvailable() {
        book.setAvailable(false);
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(booksRepository, never()).deleteById(1L);
    }

    @Test
    public void testGetAllBooks() {
        when(booksRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getTitle(), books.get(0).getTitle());
        verify(booksRepository, times(1)).findAll();
    }

    @Test
    public void testGetAvailableBooks() {
        when(booksRepository.findByAvailableTrue()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAvailableBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertTrue(books.get(0).isAvailable());
        verify(booksRepository, times(1)).findByAvailableTrue();
    }
}
