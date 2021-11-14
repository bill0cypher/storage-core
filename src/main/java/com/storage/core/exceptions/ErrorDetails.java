package com.storage.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private HttpStatus status;
    private String localizedMessage;
    private List<String> errorList;
}
