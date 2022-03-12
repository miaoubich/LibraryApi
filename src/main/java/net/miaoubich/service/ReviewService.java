package net.miaoubich.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.miaoubich.model.Review;
import net.miaoubich.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired 
	ReviewRepository reviewRepository;
	
	public Review upsertReview(Review review) {
		return reviewRepository.save(review);
	}
	
	public Review findReviewById(Long reviewId) {
		return reviewRepository.getByReviewId(reviewId);
	}
	
	public List<Review> findAll(){
		return (List<Review>) reviewRepository.findAll();
	}
}
