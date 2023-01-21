package com.store.catalogservice.service;

import com.store.catalogservice.domain.Book;
import com.store.catalogservice.exception.BookAlreadyExistsException;
import com.store.catalogservice.exception.BookNotFoundException;
import com.store.catalogservice.repo.BookRepository;
import org.springframework.stereotype.Service;

@Service // Stereotype annotation that marks a class to be a service managed by Spring
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) { // BookRepository is provided through constructor autowiring.
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
      .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.id(),
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price(),
                            existingBook.publisher(),
                            existingBook.createdDate(),
                            existingBook.lastModifiedDate(),
                            existingBook.version());
                    return bookRepository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));
    }
}
