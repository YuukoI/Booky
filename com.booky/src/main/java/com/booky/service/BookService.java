package com.booky.service;

import com.booky.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book createBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    Book getBook(Long id);

    Page<Book> getAllBooksPaged(Pageable pageable);

    Book getBookByName(String name);

    Page<Book> getBookByAuthorPaged(String author, Pageable pageable);

    Page<Book> findByKeywordPaged(String keyword, Pageable pageable);
}
