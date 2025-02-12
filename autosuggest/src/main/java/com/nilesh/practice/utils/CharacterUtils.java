package com.nilesh.practice.utils;

public class CharacterUtils {

    public static byte toLowerCase(byte character) {
        char toChar = (char) character;
        // Convert character to lowercase
        return (byte) Character.toLowerCase(toChar);
    }
}
