package com.nilesh.practice;

import java.util.HashSet;
import java.util.Set;

public class Combinator {

    public Set<String> combinations(String word, int minLength) {
        word = word.toLowerCase();
        Set<String> combinations = new HashSet<>();
        for (int i = word.length()-1; i >= 0 ; i--) {
            combinations = combine(combinations, word.charAt(i));
        }
        // 2. remove all the values less than the minLength
        combinations = cleanse(combinations, minLength); // O(n)
        return combinations;
    }

    private Set<String> cleanse(Set<String> combinations, int minLength) {
        // 1a. iterate from the end of the string
        // 1b. iterate over the values, concatenate present_char + iterated_value,
        // and add to the array
        // 1c. add the character at position i to the array.
        Set<String> newCombination = new HashSet<>();
        for (String combination : combinations) {
            if(combination.length() >= minLength)
                newCombination.add(combination);
        }
        return newCombination;
    }

    private Set<String> combine(Set<String> combinations, char currentValue) {
        Set<String> newCombination = new HashSet<>();
        for (String combination : combinations) {
            newCombination.add(currentValue + combination );
        }
        newCombination.add(String.valueOf(currentValue));
        combinations.addAll(newCombination);
        return combinations;
    }

    public static void main(String[] args) {
        int minLength = 2;
        String value = "INDIA";
        new Combinator().combinations(value, minLength).forEach(System.out::println);
    }
}
