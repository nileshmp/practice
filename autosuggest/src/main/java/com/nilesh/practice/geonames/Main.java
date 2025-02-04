package com.nilesh.practice.geonames;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        String allCountries =
            "/Users/nilesh/work/codebase/practice/autosuggest/src/main/data/allCountries.txt";
        String cities5000 =
            "/Users/nilesh/work/codebase/practice/autosuggest/src/main/data/cities5000.txt";
        FileReader reader = new FileReader(cities5000);
        CitiesParser citiesParser = new CitiesParser();
        Set<String> places = new HashSet<>();
        int count = 0;
        while (reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            String name = citiesParser.parseASCII(currentLine);
            System.out.println(name);
            places.add(name);
            System.out.println(places.size());
        }
        System.out.println(count);
        System.out.println(places.size());
    }
}
