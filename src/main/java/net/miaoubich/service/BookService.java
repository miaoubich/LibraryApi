package net.miaoubich.service;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.miaoubich.custom.exception.EmpltyFieldsException;
import net.miaoubich.custom.exception.StoreIsEmptyException;
import net.miaoubich.model.Book;
import net.miaoubich.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public Book upsertBook(Book book) {

		if (book.getBookName().isEmpty() || book.getAuthor().isEmpty() || book.getReviews().isEmpty())
			throw new EmpltyFieldsException(HttpStatus.BAD_REQUEST, "Fields are mandatory!");

		if (book.getPrice() <= 0)
			throw new IllegalArgumentException();

		if (book.getBookId() != null) {
			Book existBook = bookRepository.findById(book.getBookId()).orElse(null);
			existBook.setBookName(book.getBookName());
			existBook.setAuthor(book.getAuthor());
			existBook.setPrice(book.getPrice());
			existBook.setReviews(book.getReviews());
		}
		return bookRepository.save(book);
	}

	public Book findBookById(Long bookId) {
		if (bookRepository.getByBookId(bookId) != null)
			return bookRepository.getByBookId(bookId);
		else
			throw new NoSuchElementException();
	}

	public List<Book> findAllBooks() {
		List<Book> books = bookRepository.findAll();
		if (books.size() != 0)
			return books;
		else
			throw new StoreIsEmptyException(HttpStatus.NOT_FOUND, "Sorry, we couldn't find any book in our Library!");
	}

	public String deleteBookById(Long bookId) {
		Book deleteBook = bookRepository.findById(bookId).orElse(null);
		if (deleteBook != null) {
			bookRepository.deleteById(bookId);
			return "Successfully deleted book.";
		} else
			throw new NoSuchElementException();
	}

	public Book updateByFields(Long bookId, Map<Object, Object> fields) {
		Book existBook = findBookById(bookId);
		if (existBook != null) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findRequiredField(Book.class, (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, existBook, value);
			});
			bookRepository.save(existBook);
		} else
			throw new NoSuchElementException();
		return existBook;
	}

	public Map<String, List<Book>> groupBooksByAuthor() {
		List<Book> books = findAllBooks();
		return books.stream().collect(Collectors.groupingBy(Book::getAuthor));
	}

	public List<Book> getBooksByAuthorNameAndPrince(String author, Double price) {
		List<Book> books = findAllBooks();
		List<Book> booksByAuthorPrice = books.stream().filter(b -> b.getAuthor().equalsIgnoreCase(author))
				.filter(b -> b.getPrice() < price).collect(Collectors.toList());
		if (!booksByAuthorPrice.isEmpty())
			return booksByAuthorPrice;
		else if (booksByAuthorPrice.isEmpty())
			throw new NoSuchElementException();
		else
			throw new NullPointerException();
	}

	public List<Book> obtainBooksByRate(Double rate) {
		if (rate != 0) {
			List<Book> books = bookRepository.findAll().stream()
					.filter(b -> b.getReviews().stream().anyMatch(r -> r.getRate().equals(rate)))
					.collect(Collectors.toList());

			if (!books.isEmpty())
				return books;
			else if (books.isEmpty())
				throw new NoSuchElementException();
		}
		throw new NullPointerException();
	}

	/*
	 * List<Review> reviews = findAllBooks().stream() .map(b ->
	 * b.getReviews().stream()).collect(Collectors.toList()) .stream().flatMap(r ->
	 * r) .filter(r->r.getRate()==5) .collect(Collectors.toList());
	 */

	public List<Book> booksByPriceLimit(Double price) {
		List<Book> books = findAllBooks();
		return books.stream().filter(b -> b.getPrice() <= price).collect(Collectors.toList());
	}

	public Optional<Book> getTheCheapestBook() {
		List<Book> books = findAllBooks();
		return books.stream().min(Comparator.comparing(Book::getPrice));
	}

	public Optional<Book> mostExpensiveBook() {
		List<Book> books = findAllBooks();
		return books.stream().max(Comparator.comparing(Book::getPrice));
	}

	public Double averageBooksPrice() {
		List<Book> books = findAllBooks();
		Double averagePrice = books.stream().map(b -> b.getPrice()).collect(Collectors.toList()).stream()
				.mapToDouble(p -> p).average().getAsDouble();

		return averagePrice;
	}

	public String getStaticsFigures() {
		List<Book> books = findAllBooks();
		DoubleSummaryStatistics staticsFigures = books.stream().map(p -> p.getPrice()).collect(Collectors.toList())
				.stream().mapToDouble(a -> a).summaryStatistics();

		return "Count: " + staticsFigures.getCount() + ", Price Average: " + staticsFigures.getAverage()
				+ ", Price Max: " + staticsFigures.getMax() + ", Price Min: " + staticsFigures.getMin()
				+ ", Prices Sum: " + staticsFigures.getSum();
	}

	public List<Book> sortBooksByPrice() {
		List<Book> books = findAllBooks();
		Collections.sort(books, (b1, b2) -> {
			return b1.getPrice().compareTo(b2.getPrice());
		});
		return books;
	}

	public List<Book> sortBooksByPrice2() {
		List<Book> books = findAllBooks();
		return books.stream().sorted(Comparator.comparing(Book::getPrice)).limit(4).collect(Collectors.toList());
	}

	public List<Book> sortBooksByName() {
		List<Book> books = findAllBooks();
		Collections.sort(books, (b1, b2) -> {
			return b1.getBookName().compareTo(b2.getBookName());
		});
		return books;
	}

	public List<Book> getBooksWithReviews() {
		List<Book> books = findAllBooks();
		return books.stream().filter(b -> b.getReviews().size() != 0).collect(Collectors.toList());
	}

	public List<Book> getBooksWithNoReview() {
		List<Book> books = findAllBooks();
		return books.stream().filter(b -> b.getReviews().isEmpty()).collect(Collectors.toList());
	}

	// Map bookName to its reviews titles
	public Map<Object, Object> linkBookToReviews() {
		List<Book> books = getBooksWithReviews();
		Map<Object, Object> bookNameToReviews = books.stream().collect(
				Collectors.toMap(b -> b.getBookName(), b -> b.getReviews().stream().map(r -> r.getReviewTitle())));

		return bookNameToReviews;
	}

	public Map<Object, Object> bookNameLinkedToRate() {
		List<Book> books = getBooksWithReviews();
		Map<Object, Object> bookToRate = books.stream().collect(
				Collectors.toMap(b -> b.getBookName(), b -> b.getReviews().parallelStream().map(r -> r.getRate())));

		return bookToRate;
	}
	
	public Map<Double, List<Book>> groupBooksByPrice(){
		List<Book> books = findAllBooks();
		Map<Double, List<Book>> booksByPrice = books.stream().collect(Collectors.groupingBy(Book::getPrice));
		
		return booksByPrice;
	}
}
