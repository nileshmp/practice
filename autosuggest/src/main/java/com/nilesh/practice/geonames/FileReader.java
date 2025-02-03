package com.nilesh.practice.geonames;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class FileReader {

    private final String filePath;
    private final Iterator<String> lines;

    public FileReader(String filePath) throws IOException {
        this.filePath = filePath;
        lines = Files.lines(Paths.get(filePath)).iterator();
    }

    public String nextLine() {
        return lines.next();
    }

    public boolean hasNextLine() {
        return lines.hasNext();
    }
}
