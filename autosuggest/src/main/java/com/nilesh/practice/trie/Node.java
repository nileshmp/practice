package com.nilesh.practice.trie;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {

    private final boolean isRoot;
    private Node parent;
    private HashMap<String, Node> children;
    private String name;
    private ArrayList<String> values;

    public Node(boolean isRoot, String name) {
        this.isRoot = isRoot;
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public HashMap<String, Node> getChildren() {
        return children;
    }

    public void setChildren(HashMap<String, Node> children) {
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

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public void addValue(String value) {
        if (this.values == null) {
            this.values = new ArrayList<>();
        }
        if (value != null && !value.isEmpty() && !value.isBlank()) {
            values.add(value);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean hasChild(String name) {
        return (this.children != null && this.children.containsKey(name));
    }

    public Node getChild(String currValue) {
        if(this.children != null) {
            return this.children.get(currValue);
        }
        return null;
    }
}
