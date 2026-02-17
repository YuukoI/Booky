package com.booky.controller;

import com.booky.dto.BookRequest;
import com.booky.dto.BookResponse;
import com.booky.entity.Book;
import com.booky.mapper.BookMapper;
import com.booky.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest){
        try {
            Book book = bookMapper.toEntity(bookRequest);
            Book savedBook = bookService.createBook(book);
            BookResponse response = bookMapper.toResponse(savedBook);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.err.println("Error creating book: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity<Page<BookResponse>> findAllBooks(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "15") int size,
                                                         @RequestParam(defaultValue = "id") String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.getAllBooksPaged(pageable);
        Page<BookResponse> response = bookMapper.toResponsePage(books);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookResponse>> searchBooks(@RequestParam String keyword,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "15") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy){
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.findByKeywordPaged(keyword, pageable);
        Page<BookResponse> response = bookMapper.toResponsePage(books);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long id){
        Book book = bookService.getBook(id);
        BookResponse response = bookMapper.toResponse(book);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest){
        Book book = bookMapper.toEntity(bookRequest);
        Book updatedBook = bookService.updateBook(id, book);
        BookResponse response = bookMapper.toResponse(updatedBook);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BookResponse> findBookByName(@PathVariable String name){
        Book book = bookService.getBookByName(name);
        BookResponse response = bookMapper.toResponse(book);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Page<BookResponse>> findBookByAuthor(@PathVariable String author,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "15") int size,
                                                                @RequestParam(defaultValue = "id") String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.getBookByAuthorPaged(author, pageable);
        Page<BookResponse> response = bookMapper.toResponsePage(books);

        return ResponseEntity.ok(response);
    }
}
