package com.booky.service;

import com.booky.entity.Book;
import com.booky.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book bookUpdate = bookRepository.findById(id).orElse(null);

        if(bookUpdate == null) throw new RuntimeException("Book not found");

        if(book.getAuthor() != null){
            bookUpdate.setAuthor(book.getAuthor());
        }

        if(book.getName() != null){
            bookUpdate.setName(book.getName());
        }

        if(book.getNumPages() != null){
            bookUpdate.setNumPages(book.getNumPages());
        }

        return bookRepository.save(bookUpdate);

    }

    @Override
    public void deleteBook(Long id) {
        if(bookRepository.findById(id).isEmpty()) throw new RuntimeException("Book not found");
        bookRepository.deleteById(id);
    }

    @Override
    public Book getBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if(book == null) throw new RuntimeException("Book not found");
        return book;
    }

    @Override
    public Page<Book> getAllBooksPaged(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookByName(String name) {
        Book book = bookRepository.findByName(name);
        if(book == null) throw new RuntimeException("Book not found");
        return book;
    }

    @Override
    public Page<Book> getBookByAuthorPaged(String author, Pageable pageable) {
        return bookRepository.findByAuthor(author, pageable);
    }

    @Override
    public Page<Book> findByKeywordPaged(String keyword, Pageable pageable) {
        return bookRepository.findByKeyword(keyword, pageable);
    }

}
