package com.nilesh.practice.util;

import com.nilesh.practice.Combinator;
import com.nilesh.practice.geonames.CitiesParser;
import com.nilesh.practice.geonames.FileReader;
import com.nilesh.practice.trie.Trie;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        int minLength = 3;
        String cities5000 =
            "/Users/nilesh/work/codebase/practice/autosuggest/src/main/data/cities5000.txt";
        Main main = new Main();
        Set<String> cities = main.cities(cities5000);
        System.out.println("Total cities : "  + cities.size());
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
            System.out.println("Combinations count is " + count);
            // if (city.equalsIgnoreCase("Karungdong")) {
            //     break;
            // }
            length = Math.max(length, city.length());
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter search string : ");
            String searchString = scanner.next();
            if (searchString.equalsIgnoreCase("exit")) {
                break;
            }
            List<String> searchResult = trie.find(searchString);
            System.out.println(searchResult.size() +  " results found.");
            searchResult.forEach(System.out::println);
            System.out.println("----------------------------------------");
        }
    }

    public Set<String> cities(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        CitiesParser citiesParser = new CitiesParser();
        Set<String> places = new HashSet<>();
        while (reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            String name = citiesParser.parseASCII(currentLine);
            places.add(name);
        }
        return places;
    }
}