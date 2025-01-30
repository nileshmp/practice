package com.nilesh.kafkastreams.streams.tutorials;

import com.nilesh.kafkastreams.streams.Constants;
import com.nilesh.kafkastreams.helper.KafkaAdmin;
import com.nilesh.kafkastreams.helper.Producer;

import java.io.IOException;

import static com.nilesh.kafkastreams.streams.Constants.BOOT_STRAP_SERVERS;

public class Init {
    public static void create_and_populate_topics() throws IOException {
        KafkaAdmin kafkaAdmin = new KafkaAdmin(BOOT_STRAP_SERVERS);
        kafkaAdmin.createTopic(Constants.inputTopic, 1, 1);
        kafkaAdmin.createTopic(Constants.outputTopic, 1, 1);
        new Producer(Producer.buildProperties()).produce(Constants.inputTopic, 10000);
    }
}
