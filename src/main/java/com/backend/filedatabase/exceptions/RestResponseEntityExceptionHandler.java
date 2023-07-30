package com.backend.filedatabase.exceptions;

import com.backend.filedatabase.model.DatabaseError;
import com.backend.filedatabase.model.DatabaseErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DatabaseRunTimeException.class)
    protected ResponseEntity<Object> handle(DatabaseRunTimeException ex, WebRequest request) {
        var errorResponse = new DatabaseErrorResponse();
        var error = new DatabaseError();
        if (Objects.nonNull(ex.getCause())) {
            error.setMessage(ex.getCause().getMessage());
        } else {
            error.setMessage(ex.getMessage());
        }
        errorResponse.addErrorsItem(error);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getCause() instanceof BusinessException businessException) {
            httpStatus = businessException.getHttpStatus();
        }
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorResponse = new DatabaseErrorResponse();
        List<String> errors = ex.getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage()).toList();
        var errorsList = errors.stream().map(this::toDbError).toList();
        errorResponse.setErrors(errorsList);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private DatabaseError toDbError(String message) {
        var error = new DatabaseError();
        error.setMessage(message);
        return error;
    }

}
