package com.backend.filedatabase.model;

public class DatabaseError {

    private String message;

    public DatabaseError() {
    }

    public DatabaseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
