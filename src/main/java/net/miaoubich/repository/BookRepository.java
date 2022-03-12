package net.miaoubich.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.miaoubich.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Book getByBookId(Long bookId);
}
