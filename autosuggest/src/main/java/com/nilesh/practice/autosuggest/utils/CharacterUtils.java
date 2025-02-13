package com.nilesh.practice.autosuggest.utils;

public class CharacterUtils {

    public static byte toLowerCase(byte character) {
        char toChar = (char) character;
        // Convert character to lowercase
        return (byte) Character.toLowerCase(toChar);
    }

    public static byte[] toLowerCase(byte[] characters){
        // Convert the byte array to lowercase in-place
        for (int i = 0; i < characters.length; i++) {
            // If it's a letter, convert to lowercase
            if (characters[i] >= 'A' && characters[i] <= 'Z') {
                characters[i] += 32; // Difference between 'A' and 'a'
            }
        }
        return characters;
    }
}
