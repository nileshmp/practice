package com.nilesh.kafkastreams.springboot.config;

public class DataModel {

    String transactionId;
    String make;
    String model;
    String year;
    String saleTimestamp;
    String dealerId;
    String dealerName;
    String state;
    String price;

    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String id) {
        this.transactionId = id;
    }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getSaleTimestamp() {
        return saleTimestamp;
    }
    public void setSaleTimestamp(String saleTimestamp) {
        this.saleTimestamp = saleTimestamp;
    }
    public String getDealerName() {
        return dealerName;
    }
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDealerId() {
        return dealerId;
    }
    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}