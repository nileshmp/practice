package com.nilesh.practice.autosuggest.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RadixNode {

    private final boolean isRoot;
    private HashMap<Byte, RadixNode> children;
    private byte[] name;
    private byte nodeId;
    private Set<byte[]> values;

    public RadixNode(boolean isRoot, byte nodeId, byte[] name) {
        this.isRoot = isRoot;
        this.nodeId = nodeId;
        this.name = name;
    }

     public HashMap<Byte, RadixNode> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Byte, RadixNode> children) {
        this.children = children;
    }

    public void addChild(RadixNode childNode) {
        if (children == null) {
            children = new HashMap<>();
        }
        this.children.put(childNode.getNodeId(), childNode);
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

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean hasChild(Byte name) {
        return (this.children != null && this.children.containsKey(name));
    }

    public RadixNode getChild(Byte currValue) {
        if (this.children != null) {
            return this.children.get(currValue);
        }
        return null;
    }

    public byte getNodeId() {
        return nodeId;
    }

    public void setNodeId(byte nodeId) {
        this.nodeId = nodeId;
    }
}
