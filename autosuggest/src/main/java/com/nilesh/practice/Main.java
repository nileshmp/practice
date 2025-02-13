package com.nilesh.practice;

import com.nilesh.practice.combination.Combinator;
import com.nilesh.practice.geonames.CitiesParser;
import com.nilesh.practice.geonames.FileReader;
import com.nilesh.practice.jvm.Memory;
import com.nilesh.practice.trie.Trie;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        Combinator combinator = new Combinator();
        Memory memory = new Memory();
        Trie trie = new Trie();
        readFromFileAndBuild(combinator, memory, trie);
        // build(combinator, trie);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter search string : ");
            String searchString = scanner.next();
            if (searchString.equalsIgnoreCase("exit")) {
                break;
            }
            List<String> searchResult = trie.find(searchString);
            System.out.println(searchResult.size() + " results found.");
            searchResult.forEach(System.out::println);
            System.out.println("----------------------------------------");
        }
    }

    private static void build(Combinator combinator, Trie trie) throws IOException {
        int minLength = 3;
        Main main = new Main();
        main.combineAndBuildTrie(combinator, trie, "Bangalore".getBytes(StandardCharsets.US_ASCII),
            minLength);
    }

    private static void readFromFileAndBuild(Combinator combinator, Memory memory, Trie trie) throws IOException {
        int minLength = 3;
        String cities5000 =
            "/Users/nilesh/work/codebase/practice/autosuggest/src/main/data/cities5000.txt";
        Main main = new Main();
        Set<byte[]> cities = main.cities(cities5000);
        int count = 0;
        for (byte[] city : cities) {
            long usedMemory = memory.printSystemMemoryUsage();
            if (usedMemory > 1000) {
                System.out.println("Memory barrier breached...");
                break;
            }
            main.combineAndBuildTrie(combinator, trie, city, minLength);
            count++;
        }
        System.out.println("Total cities : " + cities.size());
        System.out.println("Cities indexed : " + count);
    }

    private int count = 0;

    private void combineAndBuildTrie(Combinator combinator, Trie trie, byte[] city, int minLength) {
        Set<byte[]> combinations = combinator.combinations(city, minLength);
        System.out.println(
            "Combination count for word " + new String(city) + " is " + combinations.size());
        count += combinations.size();
        trie.build(city, combinations);
        System.out.println("Node count is : " + trie.nodeCount());
        System.out.println("Combinations count is : " + count);
    }

    public Set<byte[]> cities(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        CitiesParser citiesParser = new CitiesParser();
        Set<byte[]> places = new HashSet<>();
        while (reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            String name = citiesParser.parseASCII(currentLine);
            // places.add(name);
            places.add(name.getBytes(StandardCharsets.US_ASCII));
        }
        return places;
    }
}