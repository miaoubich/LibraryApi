package net.miaoubich.controller.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.miaoubich.exception.BusinessException;

@ControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> handleEmptyFields(BusinessException businessException){
		return new ResponseEntity<String>("Fields are mandatory, shouldn't be empty", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handlePriceField(){
		return new ResponseEntity<String>("Price have to be a positive value greater than zero.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleElementNotFound(){
		return new ResponseEntity<String>("We are facing a shortage in this book.", HttpStatus.BAD_REQUEST);
	}
}
