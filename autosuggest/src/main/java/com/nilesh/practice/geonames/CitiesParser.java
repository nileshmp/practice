package com.nilesh.practice.geonames;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CitiesParser {

    public String parse(String value) {
        String[] tokens = value.split("\t");
        return tokens[1];
    }

    public Set<String> parseWithAliases(String value) {
        String[] tokens = value.split("\t");
        Set<String> names = new HashSet<>(1);
        names.add(tokens[1]);
        names.add(tokens[2]);
        String[] aliases = tokens[3].split(",");
        names.addAll(Arrays.asList(aliases));
        return names;
    }
}
