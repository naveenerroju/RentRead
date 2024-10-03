package com.naveen.rentread.service;

import com.naveen.rentread.domain.Book;
import com.naveen.rentread.domain.Rental;
import com.naveen.rentread.domain.User;
import com.naveen.rentread.exception.ValidationException;
import com.naveen.rentread.repos.BooksRepository;
import com.naveen.rentread.repos.RentalRepository;
import com.naveen.rentread.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BooksRepository bookRepository;

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalService rentalService;

    private User user;
    private Book book;
    private Rental rental;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAvailable(true);

        rental = new Rental();
        rental.setId(1L);
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentedAt(LocalDate.now());

        user.setRentals(new HashSet<>());
    }

    @Test
    void testRentBook_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);

        Rental createdRental = rentalService.rentBook("test@example.com", 1L);

        assertNotNull(createdRental);
        assertEquals(user, createdRental.getUser());
        assertEquals(book, createdRental.getBook());
        assertFalse(book.isAvailable());
        verify(rentalRepository, times(1)).save(any(Rental.class));
        verify(userRepository, times(1)).save(user);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testRentBook_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            rentalService.rentBook("test@example.com", 1L);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testRentBook_BookNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            rentalService.rentBook("test@example.com", 1L);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void testRentBook_BookNotAvailable() {
        book.setAvailable(false);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            rentalService.rentBook("test@example.com", 1L);
        });

        assertEquals("Book is not available for rental", exception.getMessage());
    }

    @Test
    void testReturnBook_Success() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        Rental returnedRental = rentalService.returnBook("test@example.com", 1L);

        assertNotNull(returnedRental);
        assertEquals(LocalDate.now(), returnedRental.getReturnedAt());
        assertTrue(book.isAvailable());
        verify(rentalRepository, times(1)).delete(rental);
        verify(userRepository, times(1)).save(user);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBook_RentalNotFound() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            rentalService.returnBook("test@example.com", 1L);
        });

        assertEquals("Rental not found", exception.getMessage());
    }

    @Test
    void testReturnBook_AlreadyReturned() {
        rental.setReturnedAt(LocalDate.now());
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            rentalService.returnBook("test@example.com", 1L);
        });

        assertEquals("Book has already been returned", exception.getMessage());
    }
}
