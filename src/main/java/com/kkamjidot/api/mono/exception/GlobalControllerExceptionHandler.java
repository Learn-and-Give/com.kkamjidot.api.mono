package com.kkamjidot.api.mono.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GlobalControllerExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        LOGGER.info("UNAUTHORIZED: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());     // 401 UNAUTHORIZED
    }

    @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
        LOGGER.info("FORBIDDEN: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());     // 403 FORBIDDEN
    }

    @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        LOGGER.info("NOT_FOUND: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());     // 404 DATA NOT FOUND
    }
}
