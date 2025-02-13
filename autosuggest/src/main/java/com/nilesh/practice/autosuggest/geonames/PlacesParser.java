package com.nilesh.practice.autosuggest.geonames;

public class PlacesParser {

    public String parse(String value) {
        String[] tokens = value.split("\t");
        String[] aliases = tokens[tokens.length - 2].split("/");
        return aliases[0];
    }
}
