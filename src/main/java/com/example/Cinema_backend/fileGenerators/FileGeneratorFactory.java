package com.example.Cinema_backend.fileGenerators;

import com.example.Cinema_backend.dto.OrdersDTO;

public class FileGeneratorFactory {
    public static FileGenerator createFileGenerator(FileGeneratorType type, OrdersDTO orderDTO) {
        switch (type) {
            case PDF:
                return new PdfFileGenerator(orderDTO);
            case TXT:
                return new TextFileGenerator(orderDTO);
            default:
                throw new IllegalArgumentException("Unknown file generator type");
        }
    }
}