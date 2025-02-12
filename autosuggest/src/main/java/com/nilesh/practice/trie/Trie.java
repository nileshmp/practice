package com.nilesh.practice.trie;

import static com.nilesh.practice.utils.CharacterUtils.toLowerCase;

import com.nilesh.practice.jvm.Memory;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Trie {

    private final Node rootNode;
    private final Memory memory;
    private long nodeCount = 0;

    public Trie() {
        rootNode = new Node(true, (byte)-1);
        memory = new Memory();
    }

    public long nodeCount() {
        return nodeCount;
    }

    public void build(byte[] value, Set<byte[]> combinations) {
        System.out.println("Entered building for combinations...");
        if (combinations.isEmpty()) {
            buildTrie(value, value);
        } else {
            for (byte[] combination : combinations) {
                buildTrie(value, combination);
                long usedMemory = memory.printSystemMemoryUsage();
                if (usedMemory > 1000) {
                    System.out.println("Memory barrier breached...");
                    break;
                }
            }
        }
        System.out.println("Memory consumed is : " + memory.printSystemMemoryUsage());
    }

    private void buildTrie(byte[] value, byte[] combination) {
        Node currentNode = rootNode;
        for (byte charCombo : combination) {
            // Lower case the character before adding to the Trie
            charCombo = toLowerCase(charCombo);
            if (!currentNode.hasChild(charCombo)) {
                Node newNode = new Node(false, (byte)charCombo);
                nodeCount++;
                currentNode.addChild(newNode);
                currentNode = newNode;
            } else {
                currentNode = currentNode.getChild(charCombo);
            }
        }
        currentNode.addValue(value);
    }

    public List<String> find(String search) {
        search = search.toLowerCase();
        byte[] searchBytes = search.getBytes(StandardCharsets.US_ASCII);
        Node currNode = rootNode;
        int count = 0;
        for (byte searchByte : searchBytes) {
            if (currNode.hasChild(searchByte)) {
                currNode = currNode.getChild(searchByte);
                // if this is the last character
                if (count == searchBytes.length - 1) {
                    // TODO handle cases where i=this is not the value node
                    if (currNode.isValueNode()) {
                        return currNode.getValues().stream().map(String::new).toList();
                    } else {
                        // TODO continue till we find value nodes all the way till leaf node.
                    }
                }
            }
            count++;
        }
        return Collections.emptyList();
    }
}

