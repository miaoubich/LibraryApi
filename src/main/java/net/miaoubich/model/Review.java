package net.miaoubich.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	private String reviewTitle;
	private Double rate;
	
	@ManyToOne()
	@JoinColumn(name = "fk_book_id")
	private Book book;
	
	public Review(Long reviewId, String reviewTitle, Double rate, Book book) {
		super();
		this.reviewId = reviewId;
		this.reviewTitle = reviewTitle;
		this.rate = rate;
		this.book = book;
	}
	
	public Review() {}
	
	@JsonBackReference
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Long getReviewId() {
		return reviewId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public String getReviewTitle() {
		return reviewTitle;
	}
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
}
