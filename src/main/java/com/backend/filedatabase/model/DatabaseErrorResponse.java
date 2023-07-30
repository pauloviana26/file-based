package com.backend.filedatabase.model;

import java.util.List;

public class DatabaseErrorResponse {

    private List<DatabaseError> errors;

    public DatabaseErrorResponse() {
    }

    public DatabaseErrorResponse(List<DatabaseError> errors) {
        this.errors = errors;
    }

    public List<DatabaseError> getErrors() {
        return errors;
    }

    public void setErrors(List<DatabaseError> errors) {
        this.errors = errors;
    }

    public DatabaseErrorResponse addErrorsItem(DatabaseError error) {
        this.errors.add(error);
        return this;
    }
}