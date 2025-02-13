package com.nilesh.practice.autosuggest.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Node {

    private final boolean isRoot;
    private HashMap<Byte, Node> children;
    private Byte name;
    private Set<byte[]> values;

    public Node(boolean isRoot, Byte name) {
        this.isRoot = isRoot;
        this.name = name;
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

    public Byte getName() {
        return name;
    }

    public void setName(Byte name) {
        this.name = name;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean hasChild(Byte name) {
        return (this.children != null && this.children.containsKey(name));
    }

    public Node getChild(Byte name) {
        if (this.children != null) {
            return this.children.get(name);
        }
        return null;
    }
}
