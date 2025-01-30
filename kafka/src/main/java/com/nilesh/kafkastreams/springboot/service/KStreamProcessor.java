package com.nilesh.kafkastreams.springboot.service;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nilesh.kafkastreams.springboot.config.DataModel;

@Component
public class KStreamProcessor {

    @Value("${spring.kafka.topic.texassales}")
    private String texassalestopic;

    private static final Logger logger = LoggerFactory.getLogger(KStreamProcessor.class);

    public void process(KStream<String, DataModel> stream){

        //KSTREAM FILTER: Filter the Stream to get Texas sales into a new Texas Topic
        stream.filter(new Predicate<String, DataModel>() {
            @Override
            public boolean test(String key, DataModel object) {
                logger.info(object.toString());
                if(object!=null && object.getState()!=null && object.getState().trim().equalsIgnoreCase("TEXAS")) {
                    logger.info(object.toString());
                    logger.info("Found record with TEXAS");
                    return true;
                }else {
                    return false;
                }
            }
        }).to(texassalestopic);
    }
}