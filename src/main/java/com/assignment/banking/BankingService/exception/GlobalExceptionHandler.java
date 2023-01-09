package com.assignment.banking.BankingService.exception;

import com.assignment.banking.BankingService.dto.response.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //specific exception
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(AccountNotFoundException ex,
                                                                        HttpServletRequest request) {
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.NOT_FOUND.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .timestamp(LocalDateTime.now())
                        .customMessage("Account is not created")
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<ErrorDetails> handleResourceFoundException(AccountExistException ex,
                                                                     HttpServletRequest request) {
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.FOUND.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .timestamp(LocalDateTime.now())
                        .customMessage("Request is not valid")
                        .build(), HttpStatus.FOUND);
    }

    @ExceptionHandler(BalanceMismatchException.class)
    public ResponseEntity<ErrorDetails> handleRBalanceMismatchException(BalanceMismatchException ex,
                                                                        HttpServletRequest request) {
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .timestamp(LocalDateTime.now())
                        .customMessage("the server cannot or will not process the request due to mismatch in opening and closing balance")
                        .build(), HttpStatus.NOT_FOUND);
    }

    // global exceptions

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDetails> validationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .errorMessage(errors.toString())
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Request is not valid")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex,
                                                              HttpServletRequest request) {
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .errorMessage(ex.getLocalizedMessage())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .timestamp(LocalDateTime.now())
                        .customMessage("Request is not valid")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
