package net.miaoubich.allexceptions.handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.miaoubich.custom.exception.EmpltyFieldsException;
import net.miaoubich.custom.exception.StoreIsEmptyException;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmpltyFieldsException.class)
	public ResponseEntity<String> handleEmptyFields(EmpltyFieldsException businessException) {
		return new ResponseEntity<String>("Fields are mandatory, Please fill them up", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(StoreIsEmptyException.class)
	public ResponseEntity<String> handleEmptyBooksList(StoreIsEmptyException storeIsEmptyException){
		return new ResponseEntity<String>("Sorry The Library is empty!",HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handlePriceField(IllegalArgumentException ex) {
		return new ResponseEntity<String>("Price have to be a positive value greater than zero.",
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleElementNotFoundExcpetin() {
		return new ResponseEntity<String>("We are facing a shortage in this item or it doesn't exist in our database.", HttpStatus.NOT_FOUND);
	}

	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>("Please check if you're using the appropriate request method.",
				HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException nullPointerException){
		return new ResponseEntity<String>("Please be sure to set the parameters values in the URI.", HttpStatus.BAD_REQUEST);
	}
}
