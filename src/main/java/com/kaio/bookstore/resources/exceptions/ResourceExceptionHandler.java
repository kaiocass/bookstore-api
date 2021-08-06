package com.kaio.bookstore.resources.exceptions;

import javax.servlet.ServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kaio.bookstore.service.exceptions.DataIntegrityViolationException;
import com.kaio.bookstore.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFoundException(
			ObjectNotFoundException exception, ServletRequest request) {
		
		StandartError error = StandartError.builder()
				.timestamp(System.currentTimeMillis())
				.status(HttpStatus.NOT_FOUND.value())
				.message(exception.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandartError> dataIntegrityViolationException(
			DataIntegrityViolationException exception, ServletRequest request) {
		
		StandartError error = StandartError.builder()
				.timestamp(System.currentTimeMillis())
				.status(HttpStatus.BAD_REQUEST.value())
				.message(exception.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> validationError(
			MethodArgumentNotValidException exception, ServletRequest request) {
		
		ValidationError error = (ValidationError) ValidationError.builder()
				.timestamp(System.currentTimeMillis())
				.status(HttpStatus.BAD_REQUEST.value())
				.message("Erro na validação dos campos").build();
		
		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			error.addErrors(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
