package de.esempe.workflow.boundary.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.json.bind.JsonbException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler(JsonbException.class)
	public ResponseEntity<String> handleJsonbException(final JsonbException e)
	{
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
