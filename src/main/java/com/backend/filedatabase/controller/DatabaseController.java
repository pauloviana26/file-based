package com.backend.filedatabase.controller;

import com.backend.filedatabase.exceptions.DatabaseRunTimeException;
import com.backend.filedatabase.model.DatabaseRequest;
import com.backend.filedatabase.service.DatabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Backend Coding Challenge", description = "RESTful API CRUD file-based")
@RequestMapping("/crud")
public class DatabaseController {

    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Operation(summary = "Get specific row or all rows from the database")
    @GetMapping
    public ResponseEntity<List<String>> getSpecificRowOrAllRows(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(databaseService.select(id));
        } catch (Exception e) {
            throw new DatabaseRunTimeException(e);
        }
    }

    @Operation(summary = "Insert a new row into the database")
    @ApiResponse(responseCode = "201", description = "Generated unique id for the new row")
    @PostMapping
    public ResponseEntity<Long> insertRow(@RequestBody @Valid DatabaseRequest request) {
        try {
            return new ResponseEntity<>(databaseService.insert(request.getRow()), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new DatabaseRunTimeException(e);
        }
    }

    @Operation(summary = "Update a specific row in the database")
    @ApiResponse(responseCode = "200", description = "True if the update succeeded, false otherwise")
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateRow(@PathVariable Long id, @RequestBody @Valid DatabaseRequest request) {
        try {
            return ResponseEntity.ok(databaseService.update(id, request.getRow()));
        } catch (Exception e) {
            throw new DatabaseRunTimeException(e);
        }
    }

    @Operation(summary = "Delete a specific row from the database")
    @ApiResponse(responseCode = "200", description = "True if the deletion succeeded, false otherwise")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRow(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(databaseService.delete(id));
        } catch (Exception e) {
            throw new DatabaseRunTimeException(e);
        }
    }
}
