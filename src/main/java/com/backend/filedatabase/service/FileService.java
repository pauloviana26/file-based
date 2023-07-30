package com.backend.filedatabase.service;

import com.backend.filedatabase.exceptions.BusinessException;

import java.io.File;
import java.util.List;

public interface FileService {
    List<String> readCsvFile() throws BusinessException;

    void writeCsvFile(List<String> rows) throws BusinessException;

    File getCsvFile() throws BusinessException;
}
