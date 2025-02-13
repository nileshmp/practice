package com.nilesh.practice.autosuggest;

import java.util.Set;

public interface ITrie {

    void build(byte[] city, Set<byte[]> combinations);

    long nodeCount();

    Set<String> find(String searchString);
}
