package com.naveen.rentread.service;

import com.naveen.rentread.domain.Book;
import com.naveen.rentread.domain.Rental;
import com.naveen.rentread.domain.User;
import com.naveen.rentread.repos.BooksRepository;
import com.naveen.rentread.repos.RentalRepository;
import com.naveen.rentread.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BooksRepository bookRepository;
    @Autowired
    private RentalRepository rentalRepository;

    /**
     * The @Transactional annotation is used in the rentBook method to ensure that all the database operations within the method are executed within a single transaction.
     * This is important because it guarantees atomicity: either all operations complete successfully, or none of them are applied (in case of an error).
     * @param email
     * @param bookId
     * @return
     */
    @Transactional
    public Rental rentBook(String email, Long bookId) {
        // Fetch the user by userId
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userOptional.get();

        // Fetch the book by bookId and check if available
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }
        Book book = bookOptional.get();

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for rental");
        }

        // Create a new rental and associate with user and book
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentedAt(LocalDate.now());

        // Add rental to user's rental set (JPA will manage this)
        user.getRentals().add(rental);

        // Mark the book as unavailable
        book.setAvailable(false);

        // Save both user and book, cascading the rental save
        userRepository.save(user);
        bookRepository.save(book);

        return rental;
    }

    public void returnBook(String email, Long bookId){

    }

}
