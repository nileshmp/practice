package com.nilesh.kafkastreams.config;

import java.util.List;
import java.util.Map;

public class KafkaConfig {
    String type;
    int port;
    String image;
    int replication;
    int partition;
    long retention;
    int ISR;
    int startupTimeout;
    List<Topic> topics;
    BrokerConfig brokerConfigs;
    Map<String, String> producerConfigs;
    Map<String, String>  consumerConfigs;
    String bootStrapServers;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getReplication() {
        return replication;
    }

    public void setReplication(int replication) {
        this.replication = replication;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public long getRetention() {
        return retention;
    }

    public void setRetention(long retention) {
        this.retention = retention;
    }

    public int getISR() {
        return ISR;
    }

    public void setISR(int ISR) {
        this.ISR = ISR;
    }

    public int getStartupTimeout() {
        return startupTimeout;
    }

    public void setStartupTimeout(int startupTimeout) {
        this.startupTimeout = startupTimeout;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public BrokerConfig getBrokerConfigs() {
        return brokerConfigs;
    }

    public void setBrokerConfigs(BrokerConfig brokerConfigs) {
        this.brokerConfigs = brokerConfigs;
    }

    public Map<String, String> getProducerConfigs() {
        return producerConfigs;
    }

    public void setProducerConfigs(Map<String, String> producerConfigs) {
        this.producerConfigs = producerConfigs;
    }

    public Map<String, String> getConsumerConfigs() {
        return consumerConfigs;
    }

    public void setConsumerConfigs(Map<String, String> consumerConfigs) {
        this.consumerConfigs = consumerConfigs;
    }

    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String bootStrapServers) {
        this.bootStrapServers = bootStrapServers;
    }
}
