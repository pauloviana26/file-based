package com.backend.filedatabase.service.impl;

import com.backend.filedatabase.exceptions.BusinessException;
import com.backend.filedatabase.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseServiceImplTest {

    @Mock
    FileService fileService;

    @InjectMocks
    DatabaseServiceImpl service;

    List<String> rows;

    @BeforeEach
    void setUp() {
        rows = new ArrayList<>();
        rows.add("1,John,Doe");
        rows.add("2,Paulo,Viana");
        rows.add("3,Jose,Maria");
        rows.add("4,Joao,Any");
        rows.add("5,Mauricio,Kal");
        rows.add("6,Rony,Russ");
    }

    @Test
    void selectAllRows() throws BusinessException {
        Long id = -1L;
        when(fileService.readCsvFile()).thenReturn(rows);
        var response = service.select(id);
        assertNotNull(response);
        assertEquals(6, response.size());
        assertEquals(rows.get(0), response.get(0));
    }

    @Test
    void selectWithValidId() throws BusinessException {
        Long id = 1L;
        when(fileService.readCsvFile()).thenReturn(rows);
        var response = service.select(id);
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(rows.get(0), response.get(0));
    }

    @Test
    void selectNonExistingId() throws BusinessException {
        Long id = 8L;
        when(fileService.readCsvFile()).thenReturn(rows);
        var response = service.select(id);
        assertTrue(response.isEmpty());
    }

    @Test
    void insert() throws BusinessException {
        String newRow = "Mark,Johnson";
        when(fileService.readCsvFile()).thenReturn(rows);
        Long generatedId = service.insert(newRow);
        assertNotNull(generatedId);
        assertEquals(7L, generatedId);
    }

    @Test
    void update() throws BusinessException {
        Long id = 6L;
        String newRow = "Updated,Name";
        when(fileService.readCsvFile()).thenReturn(rows);
        boolean updated = service.update(id, newRow);
        assertTrue(updated);
    }

    @Test
    void updateWithNonExistingId() throws BusinessException {
        Long id = 10L;
        String newRow = "New,Row";
        when(fileService.readCsvFile()).thenReturn(rows);
        boolean updated = service.update(id, newRow);
        assertFalse(updated);
    }

    @Test
    void delete() throws BusinessException {
        Long id = 1L;
        when(fileService.readCsvFile()).thenReturn(rows);
        boolean deleted = service.delete(id);
        assertTrue(deleted);
    }

    @Test
    void deleteWithInvalidId() throws BusinessException {
        Long id = 10L;
        when(fileService.readCsvFile()).thenReturn(rows);
        boolean deleted = service.delete(id);
        assertFalse(deleted);
    }
}