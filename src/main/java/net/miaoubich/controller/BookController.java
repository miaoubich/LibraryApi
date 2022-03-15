package net.miaoubich.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.miaoubich.model.Book;
import net.miaoubich.service.BookService;

@RestController
@CrossOrigin
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/upsert")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book upsertBook = bookService.upsertBook(book);
		return new ResponseEntity<Book>(upsertBook, HttpStatus.CREATED);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
		Book findBook = bookService.findBookById(bookId);
		return new ResponseEntity<Book>(findBook, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> findAllBooks = bookService.findAllBooks();
		return new ResponseEntity<List<Book>>(findAllBooks, HttpStatus.FOUND);
	}

	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
		String deleteBook = bookService.deleteBookById(bookId);
		return new ResponseEntity<String>(deleteBook, HttpStatus.OK);
	}

	@PatchMapping("/update/{bookId}")
	public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Map<Object, Object> fields) {
		Book updateByFields = bookService.updateByFields(bookId, fields);
		return new ResponseEntity<Book>(updateByFields, HttpStatus.OK);
	}

	@GetMapping("/groupByAuthor")
	public Map<String, List<Book>> groupBooksByAuthor() {
		Map<String, List<Book>> booksByAuthor = bookService.groupBooksByAuthor();
		return booksByAuthor;
	}

	@GetMapping("/booksByAuthorNameAndPrice")
	public List<Book> booksByAUthorAndPrice(@RequestParam(value = "authorName") String author,
			@RequestParam(value = "price", required = false) Double price) {
		List<Book> books = bookService.getBooksByAuthorNameAndPrince(author, price);
		return books;
	}

	@GetMapping("/booksByRate")
	public ResponseEntity<List<Book>> booksByRate(@RequestParam(value = "rate", required = false) Double rate) {
		List<Book> books = bookService.obtainBooksByRate(rate);
		return new ResponseEntity<List<Book>>(books, HttpStatus.FOUND);
	}

	@GetMapping("/booksByPrice")
	public ResponseEntity<List<Book>> booksListByPrice(@RequestParam(value = "price", required = false) Double price) {
		List<Book> books = bookService.booksByPriceLimit(price);
		return new ResponseEntity<List<Book>>(books, HttpStatus.FOUND);
	}

	@GetMapping("/cheapest-book")
	public ResponseEntity<Optional<Book>> cheapestBook() {
		Optional<Book> books = bookService.getTheCheapestBook();
		return new ResponseEntity<Optional<Book>>(books, HttpStatus.FOUND);
	}

	@GetMapping("/sort-byPrice")
	public ResponseEntity<List<Book>> sortBooksByPrice() {
		List<Book> books = bookService.sortBooksByPrice2();
		return new ResponseEntity<List<Book>>(books, HttpStatus.FOUND);
	}

	@GetMapping("/sort-byName")
	public ResponseEntity<List<Book>> sortBooksByBookName() {
		List<Book> books = bookService.sortBooksByName();
		return new ResponseEntity<List<Book>>(books, HttpStatus.FOUND);
	}

	@GetMapping("/book-with-noReview")
	public ResponseEntity<List<Book>> booksWithNoReview() {
		List<Book> books = bookService.getBooksWithNoReview();
		return new ResponseEntity<List<Book>>(books, HttpStatus.FOUND);
	}

	@GetMapping("/book-with-review")
	public ResponseEntity<List<Book>> booksWithReview() {
		List<Book> books = bookService.getBooksWithReviews();
		return new ResponseEntity<List<Book>>(books, HttpStatus.FOUND);
	}

	@GetMapping("/average-price")
	public ResponseEntity<Double> averageBooksPrice() {
		Double averagePrice = bookService.averageBooksPrice();
		return new ResponseEntity<Double>(averagePrice, HttpStatus.OK);
	}

	@GetMapping("/statistics-figures")
	public ResponseEntity<String> getStatisticsFigures() {
		String statisticsFigures = bookService.getStaticsFigures();
		return new ResponseEntity<String>(statisticsFigures, HttpStatus.OK);
	}

	@GetMapping("/bookName-by-reviews")
	public ResponseEntity<Map<Object, Object>> bookNameByReviews() {
		Map<Object, Object> books = bookService.linkBookToReviews();
		return new ResponseEntity<Map<Object, Object>>(books, HttpStatus.OK);
	}
	
	@GetMapping("/bookName-by-rate")
	public ResponseEntity<Map<Object, Object>> getbookNamesLinkedToItsReviewRates(){
		Map<Object, Object> books = bookService.bookNameLinkedToRate();
		return new ResponseEntity<Map<Object, Object>>(books, HttpStatus.OK);
	}
}
