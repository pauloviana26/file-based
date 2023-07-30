package com.backend.filedatabase.model;

import jakarta.validation.constraints.NotEmpty;

public class DatabaseRequest {

    @NotEmpty(message = "Can not be null or empty")
    private String row;

    public DatabaseRequest() {
    }

    public DatabaseRequest(String row) {
        this.row = row;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
