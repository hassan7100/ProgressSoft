package org.progresssoft.Utils;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class CsvToRowsHelper {
    public List<String[]> readCsvFile(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
        } catch (CsvException e) {
            throw new RuntimeException();
        }
    }
}
