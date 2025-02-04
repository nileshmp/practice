package com.nilesh.practice.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Node {

    private final boolean isRoot;
    private Node parent;
    private HashMap<Byte, Node> children;
    private byte name;
    private Set<byte[]> values;

    public Node(boolean isRoot, byte name) {
        this.isRoot = isRoot;
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public HashMap<Byte, Node> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Byte, Node> children) {
        this.children = children;
    }

    public void addChild(Node childNode) {
        if (children == null) {
            children = new HashMap<>();
        }
        this.children.put(childNode.getName(), childNode);
    }

    public boolean isValueNode() {
        return (this.values != null && !this.values.isEmpty());
    }

    public Set<byte[]> getValues() {
        return values;
    }

    public void setValues(Set<byte[]> values) {
        this.values = values;
    }

    public void addValue(byte[] value) {
        if (this.values == null) {
            this.values = new HashSet<>();
        }
        if (value != null && value.length > 0) {
            values.add(value);
        }
    }

    public byte getName() {
        return name;
    }

    public void setName(byte name) {
        this.name = name;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean hasChild(byte name) {
        return (this.children != null && this.children.containsKey(name));
    }

    public Node getChild(byte currValue) {
        if(this.children != null) {
            return this.children.get(currValue);
        }
        return null;
    }
}
