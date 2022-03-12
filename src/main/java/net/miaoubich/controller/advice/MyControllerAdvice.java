package net.miaoubich.controller.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.miaoubich.exception.BusinessException;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> handleEmptyFields(BusinessException businessException) {
		return new ResponseEntity<String>("Fields are mandatory, shouldn't be empty", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handlePriceField(IllegalArgumentException ex) {
		return new ResponseEntity<String>("Price have to be a positive value greater than zero.",
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleElementNotFoundExcpetin() {
		return new ResponseEntity<String>("We are facing a shortage in this book.", HttpStatus.NOT_FOUND);
	}

	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>("Please change your http reuest method to the appropriate one",
				HttpStatus.METHOD_NOT_ALLOWED);
	}
}
