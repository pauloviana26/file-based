package com.backend.filedatabase.exceptions;

public class DatabaseRunTimeException extends RuntimeException {
    public DatabaseRunTimeException(Throwable throwable) {
        super(throwable);
    }
}
