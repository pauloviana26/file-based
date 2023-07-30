package com.backend.filedatabase.service.impl;

import com.backend.filedatabase.exceptions.BusinessException;
import com.backend.filedatabase.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    public static final String SOME_ERROR_HAS_OCCURRED_WHILE_READING_CSV_FILE = "Some error has occurred while reading csv file.";
    public static final String SOME_ERROR_OCCURRED_WHILE_WRITING_CSV_FILE = "Some error occurred while writing csv file.";

    public static final String CSV_FILE_NOT_FOUND_OR_INACCESSIBLE = "CSV file not found or inaccessible.";

    private final ResourceLoader resourceLoader;

    public FileServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<String> readCsvFile() throws BusinessException {
        List<String> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getCsvFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }
        } catch (IOException e) {
            throw new BusinessException(SOME_ERROR_HAS_OCCURRED_WHILE_READING_CSV_FILE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
        return rows;
    }

    @Override
    public void writeCsvFile(List<String> rows) throws BusinessException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getCsvFile()))) {
            for (String row : rows) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new BusinessException(SOME_ERROR_OCCURRED_WHILE_WRITING_CSV_FILE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public File getCsvFile() throws BusinessException {
        try {
            Resource resource = resourceLoader.getResource("classpath:database.csv");
            return resource.getFile();
        } catch (IOException e) {
            throw new BusinessException(CSV_FILE_NOT_FOUND_OR_INACCESSIBLE, HttpStatus.NOT_FOUND, e);
        }
    }
}
