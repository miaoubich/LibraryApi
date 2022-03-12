package net.miaoubich.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.miaoubich.model.Book;
import net.miaoubich.service.BookService;

@RestController
@CrossOrigin
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PostMapping("")
	public ResponseEntity<Book> createBook(@RequestBody Book book){
		return new ResponseEntity<Book>(bookService.upsertBook(book), HttpStatus.CREATED);
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId){
		return new ResponseEntity<Book>(bookService.findBookById(bookId), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public List<Book> getAllBooks(){
		return bookService.findAll();
	}
	
	
}
