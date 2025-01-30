package com.nilesh.kafkastreams.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class FileReader {
    private final BufferedReader bufferedReader;
    private String currLine;

    public FileReader(String inputFile) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new java.io.FileReader(new File(inputFile)));
    }

    public boolean hasNext() throws IOException {
        String line = bufferedReader.readLine();
        if (Objects.nonNull(line)) {
            this.currLine = line;
            return true;
        }
        this.currLine = "";
        return false;
    }

    public String next() throws IOException {
        return this.currLine;
    }
}
