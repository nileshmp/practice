package com.nilesh.practice.autosuggest.combination;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class Combinator {

    public Set<byte[]> combinations(byte[] word, int minLength) {
        // word = word.toLowerCase();
        Set<byte[]> combinations = new HashSet<>();
        for (int i = word.length-1; i >= 0 ; i--) {
            byte character = word[i];
            combinations = combine(combinations, character);
        }
        // for (int i = word.length()-1; i >= 0 ; i--) {
        //     combinations = combine(combinations, word.charAt(i));
        // }
        // 2. remove all the values less than the minLength
        combinations = cleanse(combinations, minLength); // O(n)
        return combinations;
    }

    private Set<byte[]> cleanse(Set<byte[]> combinations, int minLength) {
        // 1a. iterate from the end of the string
        // 1b. iterate over the values, concatenate present_char + iterated_value,
        // and add to the array
        // 1c. add the character at position i to the array.
        Set<byte[]> newCombination = new HashSet<>();
        for (byte[] combination : combinations) {
            if (combination.length >= minLength) {
                newCombination.add(combination);
            }
        }
        return newCombination;
    }

    private Set<byte[]> combine(Set<byte[]> combinations, byte currentValue) {
        Set<byte[]> newCombination = new HashSet<>();
        for (byte[] combination : combinations) {
            newCombination.add(append(currentValue, combination));
        }
        newCombination.add(new byte[] {currentValue});
        combinations.addAll(newCombination);
        return combinations;
    }

    private byte[] append(byte currentValue, byte[] combination) {
        byte[] newArray = new byte[combination.length + 1];
        // Copy the original array into the new array
        System.arraycopy(combination, 0, newArray, 1, combination.length);
        newArray[0] = currentValue;
        return newArray;
    }

    public static void main(String[] args) {
        int minLength = 2;
        String value = "INDIA";
        new Combinator().combinations(value.getBytes(StandardCharsets.US_ASCII), minLength)
            .forEach(System.out::println);
    }
}
