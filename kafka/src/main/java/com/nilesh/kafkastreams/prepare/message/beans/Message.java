package com.nilesh.kafkastreams.prepare.message.beans;

public class Message {
    int iterate;
    Key key;
    Value value;
    String topics;
    String mix;

    public int getIterate() {
        return iterate;
    }

    public void setIterate(int iterate) {
        this.iterate = iterate;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getMix() {
        return this.mix.split("-")[0];
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    public static class Key {
        String value;
        String type;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Value {
        String value;
        String type;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
