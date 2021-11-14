package com.storage.core.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RESTCoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleEmptyDataConflict(RuntimeException ex, WebRequest req) {
        return handleExceptionInternal(ex, new ErrorDetails(
                HttpStatus.NOT_FOUND,
                        "Data you're trying to manipulate with doesn't exist or wasn't provided.",
                        Collections.singletonList(ex.getLocalizedMessage())
                ),
                new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,
                "The provided data doesn't match requirements.", errorList);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
    }
}
