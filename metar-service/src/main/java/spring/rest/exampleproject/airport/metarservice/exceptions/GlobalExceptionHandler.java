package spring.rest.exampleproject.airport.metarservice.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();		
		body.put("errors", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("errors", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("errors", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("errors", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

}
