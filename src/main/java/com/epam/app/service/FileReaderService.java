package com.epam.app.service;

import java.util.List;

public interface FileReaderService {
    List<String> readAll(String filePath);
}
