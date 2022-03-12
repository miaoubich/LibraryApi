package net.miaoubich.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.miaoubich.exception.BusinessException;
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
		return (List<Book>) bookRepository.findAll();
	}
}
