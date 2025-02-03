package com.nilesh.practice.trie;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Trie {

    private final Node rootNode;
    private long nodeCount = 0;

    public Trie() {
        rootNode = new Node(true, "");

    }

    public long nodeCount() {
        return nodeCount;
    }

    public void build(String value, Set<String> combinations) {
        for (String combination : combinations) {
            Node currentNode = rootNode;
            // System.out.println("Printing combination : " + combination);
            char[] charArray = combination.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                String currentChar = String.valueOf(charArray[i]);
                // System.out.println(currentChar);
                if (!currentNode.hasChild(currentChar)) {
                    Node newNode = new Node(false, currentChar);
                    nodeCount++;
                    currentNode.addChild(newNode);
                    currentNode = newNode;
                } else {
                    currentNode = currentNode.getChild(currentChar);
                }
            }
            // currentNode.addValue(value.intern());
            currentNode.addValue(value);
        }
    }

    public List<String> find(String search) {
        search = search.toLowerCase();
        Node currNode = rootNode;
        char[] charArray = search.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            String currValue = String.valueOf(charArray[i]);
            // if this is the last character
            if (i == charArray.length - 1) {
                // TODO handle cases where i=this is not the value node
                if (currNode.isValueNode()) {
                    return currNode.getValues();
                } else {
                    // TODO continue till we find value nodes all the way till leaf node.
                }
            }
            if (currNode.hasChild(currValue)) {
                currNode = currNode.getChild(currValue);
            }
        }
        return Collections.emptyList();
    }
}

