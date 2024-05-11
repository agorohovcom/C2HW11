package com.agorohov.employeebookwithmaven.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Если не использовать ExceptionHandler, выводит указанную ошибку в браузер
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedNameException extends RuntimeException {
    public UnsupportedNameException(String message) {
        super(message);
    }
}
