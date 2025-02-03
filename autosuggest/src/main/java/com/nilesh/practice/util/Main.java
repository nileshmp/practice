package com.nilesh.practice.util;

import com.nilesh.practice.Combinator;
import com.nilesh.practice.geonames.CitiesParser;
import com.nilesh.practice.geonames.FileReader;
import com.nilesh.practice.trie.Trie;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        int minLength = 3;
        String cities5000 =
            "/Users/nilesh/work/codebase/practice/autosuggest/src/main/resources/cities5000.txt";
        Main main = new Main();
        Set<String> cities = main.cities(cities5000);
        Combinator combinator = new Combinator();
        Trie trie = new Trie();
        int count = 0;
        int length = 0;
        for (String city : cities) {
            // city = city.intern();
            Set<String> combinations = combinator.combinations(city, minLength);
            System.out.println("Combination count for word " + city + " is " + combinations.size());
            count += combinations.size();
            trie.build(city, combinations);
            System.out.println("Node count is : " + trie.nodeCount());
            System.out.println(count);
            if (city.equalsIgnoreCase("Karungdong")) {
                break;
            }
            length = Math.max(length, city.length());
        }
        System.out.println("Highest length " + length);
        List<String> searchResult = trie.find("gng");
        System.out.println(searchResult.size());
        searchResult.forEach(System.out::println);
    }

    public Set<String> cities(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        CitiesParser citiesParser = new CitiesParser();
        Set<String> places = new HashSet<>();
        while (reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            String name = citiesParser.parse(currentLine);
            places.add(name);
        }
        return places;
    }
}