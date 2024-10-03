package com.naveen.rentread.service;

import com.naveen.rentread.domain.Book;
import com.naveen.rentread.domain.Rental;
import com.naveen.rentread.domain.User;
import com.naveen.rentread.exception.ValidationException;
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
            throw new ValidationException("User not found");
        }
        User user = userOptional.get();

        // Fetch the book by bookId and check if available
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new ValidationException("Book not found");
        }
        Book book = bookOptional.get();

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for rental");
        }

        // Create a new rental object
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentedAt(LocalDate.now());

        // Add rental to user's rentals and mark the book as unavailable
        user.getRentals().add(rental);
        book.setAvailable(false);

        // Save the rental (this will assign an ID to the rental object)
        Rental savedRental = rentalRepository.save(rental);

        // Save changes to user and book as well
        userRepository.save(user);
        bookRepository.save(book);

        // Return the saved rental with the generated ID
        return savedRental;
    }


    @Transactional
    public Rental returnBook(String email, Long rentalId) {
        // Fetch the rental by rentalId
        Optional<Rental> rentalOptional = rentalRepository.findById(rentalId);
        if (rentalOptional.isEmpty()) {
            throw new ValidationException("Rental not found");
        }
        Rental rental = rentalOptional.get();

        // Check if the book has already been returned
        if (rental.getReturnedAt() != null) {
            throw new IllegalStateException("Book has already been returned");
        }

        // Fetch the user and remove the rental from the user's rental list
        User user = rental.getUser();
        user.getRentals().remove(rental);  // Remove the rental from the user's rental set

        // Fetch the book and mark it as available
        Book book = rental.getBook();
        book.setAvailable(true);

        // Update the rental with the returned date
        rental.setReturnedAt(LocalDate.now());

        // Save the user (to update their rental list), the book, and the updated rental
        userRepository.save(user);
        bookRepository.save(book);
        rentalRepository.delete(rental);  // Optionally delete the rental, since it's no longer needed

        return rental;  // Return the updated rental, even though it will be removed
    }

}
