package net.miaoubich.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import net.miaoubich.model.Review;
import net.miaoubich.service.ReviewService;

@Controller
@CrossOrigin
@RequestMapping("/api/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("")
	public ResponseEntity<Review> createReview(@RequestBody Review review){
		return new ResponseEntity<Review>(reviewService.upsertReview(review), HttpStatus.CREATED);
	}
	
	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
		return new ResponseEntity<Review>(reviewService.findReviewById(reviewId), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public List<Review> getAllReviews(){
		return reviewService.findAll();
	}
}
