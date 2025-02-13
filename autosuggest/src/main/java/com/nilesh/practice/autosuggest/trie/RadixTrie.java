package com.nilesh.practice.autosuggest.trie;

import com.nilesh.practice.autosuggest.ITrie;
import com.nilesh.practice.autosuggest.jvm.Memory;
import com.nilesh.practice.autosuggest.utils.CharacterUtils;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RadixTrie implements ITrie {

    private final RadixNode rootNode;
    private final Memory memory;
    private long nodeCount = 0;

    public RadixTrie() {
        rootNode = new RadixNode(true, (byte) -1, new byte[] {-1});
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
        combination = CharacterUtils.toLowerCase(combination);
        // System.out.printf("Building Trie for value %s and combination %s \n", new String(value),
        //     new String(combination));
        RadixNode currentNode = rootNode;
        for (int index = 0; index < combination.length; index++) {
            byte charCombo = combination[index];
            if (!currentNode.hasChild(charCombo)) {
                RadixNode newNode = new RadixNode(false, charCombo,
                    Arrays.copyOfRange(combination, index, combination.length));
                nodeCount++;
                currentNode.addChild(newNode);
                newNode.addValue(value);
                break;
            } else {
                // if the node matches get the node name and try to match the
                // name as far as possible
                RadixNode childNode = currentNode.getChild(charCombo);
                byte[] name = childNode.getName();
                // as we are trying to add the same combination again
                if (Arrays.equals(name, combination)) {
                    System.out.println("returning as same combination exists.");
                    return;
                }
                int nameIndex = 0;
                while ((nameIndex < name.length && index < combination.length) &&
                    name[nameIndex] == combination[index]) {
                    nameIndex++;
                    index++;
                }
                // we have matched the current name completely with the given combination
                if (name.length > 0 && nameIndex == name.length) {
                    if (combination.length > nameIndex) {
                        // go to the child node.
                        currentNode = childNode;
                        index--;
                        continue;
                    }
                }
                // since we have a node which needs a split
                byte[] currentNodeName = Arrays.copyOfRange(name, 0, nameIndex);
                byte[] childNodeName = Arrays.copyOfRange(name, nameIndex, name.length);
                // prepare current node
                childNode.setName(currentNodeName);
                RadixNode childSplitNode = new RadixNode(false, name[nameIndex], childNodeName);
                nodeCount++;
                HashMap<Byte, RadixNode> children = childNode.getChildren();
                if (children != null) {
                    for (RadixNode radixNode : children.values()) {
                        childSplitNode.addChild(radixNode);
                    }
                }
                childSplitNode.setValues(childNode.getValues());
                childNode.setChildren(null);
                childNode.setValues(null);
                childNode.addChild(childSplitNode);
                // prepare new node
                if (index == combination.length) {
                    childNode.addValue(value);
                } else {
                    RadixNode newSplitNode = new RadixNode(false, combination[index],
                        Arrays.copyOfRange(combination, index, combination.length));
                    nodeCount++;
                    newSplitNode.addValue(value);
                    childNode.addChild(newSplitNode);
                }
                break;
            }
        }
    }

    public Set<String> find(String search) {
        search = search.toLowerCase();
        byte[] searchBytes = search.getBytes(StandardCharsets.US_ASCII);
        RadixNode currNode = rootNode;
        for (int index = 0; index < searchBytes.length; index++) {
            byte searchByte = searchBytes[index];
            if (currNode.hasChild(searchByte)) {
                currNode = currNode.getChild(searchByte);
                byte[] name = currNode.getName();
                int nameIndex = 0;
                while ((nameIndex < name.length && index < searchBytes.length) &&
                    name[nameIndex] == searchBytes[index]) {
                    nameIndex++;
                    index++;
                }
                // move on to the next node
                if (nameIndex == name.length && index < searchBytes.length) {
                    index--;
                    continue;
                }
                // continue to get values from the sub-tree
                Set<String> values = new HashSet<>();
                findValuesInSubTree(currNode, values);
                return values;
            }
        }
        return Collections.emptySet();
    }

    private void findValuesInSubTree(RadixNode node, Set<String> values) {
        if (node.isValueNode()) {
            values.addAll(node.getValues().stream().map(String::new).collect(Collectors.toSet()));
        }
        HashMap<Byte, RadixNode> children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            for (RadixNode child : children.values()) {
                findValuesInSubTree(child, values);
            }
        }
    }
}

