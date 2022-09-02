package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.util.CsvUtils;
import com.nicouema.bank.domain.usecase.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {



    @Override
    @Transactional
    public File createFileCsv(Class<?> c, List<?> objects) throws IllegalAccessException {

        String fileName = generateFileName(c);

        String csvString = generateCsvString(objects);

        File file = new File(fileName);

        writeFileCsv(file, csvString);

        return file;
    }

    private String generateCsvString(List<?> list) throws IllegalAccessException {
        if (list.isEmpty()){
            return null;
        }

        StringBuilder csvString = new StringBuilder();
        String columns = "";
        for (Object o:list) {

            if (columns.isBlank()) {
                columns = CsvUtils.generateColumnsCsv(o);
                csvString.append(columns);
            }

            String oCsv = CsvUtils.objectToCsv(o);

            csvString.append(oCsv);
        }


        return csvString.toString();
    }



    private String generateFileName(Class<?> c) {

        return  c.getSimpleName().toUpperCase() + "REPORT_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".csv";

    }


    private void writeFileCsv(File file, String csvString) {
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(csvString);
            myWriter.close();
        }
        catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
