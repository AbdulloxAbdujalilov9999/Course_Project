package com.epam.app.service.impl;

import com.epam.app.service.FileReaderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static com.epam.app.constant.AnsiColorConstants.ANSI_RED;
import static com.epam.app.constant.AnsiColorConstants.ANSI_RESET;

public class FileReaderServiceImpl implements FileReaderService {
    public List<String> readAll(String filePath) {
        List<String> stringList;
        try {
            stringList = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Exception while reading users from file. Caused by: " + e.getMessage() + ANSI_RESET);
            return Collections.emptyList();
        }
        stringList.remove(0);
        return stringList;
    }
}
