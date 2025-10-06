package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequest(BadRequestException bre) {
		return new ResponseEntity<>(bre.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<String> handleResourceNotFound(ResourceNotFound rnfe) {
		return new ResponseEntity<>(rnfe.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<String> handleSecurityException(SecurityException se) {
		return new ResponseEntity<>(se.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ade) {
		return new ResponseEntity<>("Access denied: Insufficient permissions", HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(InvalidTokenException ite) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token: " + ite.getMessage());
    }

}
