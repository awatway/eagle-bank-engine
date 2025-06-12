package com.eagle.feature.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
class EagleBankExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<?> handleNoSuchElement(IncorrectResultSizeDataAccessException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
        return ResponseEntity.of(problem).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        return ResponseEntity.of(problem).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(FORBIDDEN, ex.getMessage());
        return ResponseEntity.of(problem).build();
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handleInsufficientFundsException(InsufficientFundsException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, ex.getMessage());
        return ResponseEntity.of(problem).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.of(problem).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("MethodArgumentNotValidException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
        return ResponseEntity.of(problem).build();
    }
}
