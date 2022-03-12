package net.miaoubich.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.miaoubich.exception.BusinessException;
import net.miaoubich.exception.StoreIsEmptyException;
import net.miaoubich.model.Book;
import net.miaoubich.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public Book upsertBook(Book book) {

		if (book.getBookName().isEmpty() || book.getAuthor().isEmpty() || book.getReviews().isEmpty())
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Fields are mandatory!");

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

	public List<Book> findAll() {
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
	
}
