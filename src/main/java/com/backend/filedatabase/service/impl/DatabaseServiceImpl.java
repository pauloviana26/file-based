package com.backend.filedatabase.service.impl;

import com.backend.filedatabase.exceptions.BusinessException;
import com.backend.filedatabase.service.DatabaseService;
import com.backend.filedatabase.service.FileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private static final Object lock = new Object();

    private final FileService fileService;

    public DatabaseServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<String> select(Long id) throws BusinessException {
        List<String> rows = readCsvFile();
        if (id == -1)
            return rows;
        else {
            List<String> result = new ArrayList<>();
            for (String row : rows) {
                String[] rowData = row.split(",");
                Long rowId = Long.parseLong(rowData[0]);
                if(rowId.equals(id)) {
                    result.add(row);
                    break;
                }
            }
            return result;
        }
    }

    @Override
    public Long insert(String row) throws BusinessException {
        synchronized (lock) {
            List<String> rows = readCsvFile();
            Long id = generateUniqueId(rows);
            String newRow = id + "," + row;
            rows.add(newRow);
            writeCsvFile(rows);
            return id;
        }
    }

    @Override
    public boolean update(Long id, String newRow) throws BusinessException {
        synchronized (lock) {
            List<String> rows = readCsvFile();
            boolean updated = false;
            for (int i = 0; i < rows.size(); i++) {
                String row = rows.get(i);
                String[] rowData = row.split(",");
                Long rowId = Long.parseLong(rowData[0]);
                if (rowId.equals(id)) {
                    rows.set(i, id + "," + newRow);
                    updated = true;
                    break;
                }
            }
            if (updated) {
                writeCsvFile(rows);
            }
            return updated;
        }
    }

    @Override
    public boolean delete(Long id) throws BusinessException {
        synchronized (lock) {
            List<String> rows = readCsvFile();
            boolean deleted = false;
            for (int i = 0; i < rows.size(); i++) {
                String row = rows.get(i);
                String[] rowData = row.split(",");
                Long rowId = Long.parseLong(rowData[0]);
                if (rowId.equals(id)) {
                    rows.remove(i);
                    deleted = true;
                    break;
                }
            }
            if (deleted) {
                writeCsvFile(rows);
            }
            return deleted;
        }
    }

    private List<String> readCsvFile() throws BusinessException {
        return fileService.readCsvFile();
    }

    private void writeCsvFile(List<String> rows) throws BusinessException {
        fileService.writeCsvFile(rows);
    }

    private Long generateUniqueId(List<String> rows) {
        long maxId = -1L;
        for (String row : rows) {
            String[] rowData = row.split(",");
            long rowId = Long.parseLong(rowData[0]);
            if (rowId > maxId && rowId != 0) {
                maxId = rowId;
            }
        }
        return maxId + 1;
    }
}
