package com.backend.filedatabase.service.impl;

import com.backend.filedatabase.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    public static final String CSV_FILE_NOT_FOUND_OR_INACCESSIBLE = "CSV file not found or inaccessible.";

    @Mock
    ResourceLoader resourceLoader;

    @Mock
    Resource resource;

    @InjectMocks
    FileServiceImpl service;

    private File csvFile;

    private List<String> testData;


    @BeforeEach
    void setUp() throws IOException {
        csvFile = File.createTempFile("test", ".csv" );
        csvFile.deleteOnExit();

        testData = new ArrayList<>();
        testData.add("1,Jon,Doe");
        testData.add("2,Jane,Doe");
    }

    @Test
    void readCsvFile() throws IOException, BusinessException {
        when(resourceLoader.getResource("classpath:database.csv")).thenReturn(resource);
        when(resource.getFile()).thenReturn(csvFile);

        service.writeCsvFile(testData);

        var response = service.readCsvFile();

        assertNotNull(response);
        assertEquals(testData, response);
    }

    @Test
    void writeCsvFile() throws BusinessException, IOException {
        when(resourceLoader.getResource("classpath:database.csv")).thenReturn(resource);
        when(resource.getFile()).thenReturn(csvFile);
        service.writeCsvFile(testData);

        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            fail("Error reading test CSV file");
        }
        assertEquals(testData, list);
    }

    @Test
    void getCsvFile() throws BusinessException, IOException {
        when(resourceLoader.getResource("classpath:database.csv")).thenReturn(resource);
        when(resource.getFile()).thenReturn(csvFile);

        File response = service.getCsvFile();

        assertNotNull(response);
        assertEquals(csvFile, response);
    }
}