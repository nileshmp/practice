package com.nilesh.kafkastreams.main;

import com.nilesh.kafkastreams.config.KafkaConfig;
import com.nilesh.kafkastreams.config.KafkaYamlConfiguration;
import com.nilesh.kafkastreams.config.Topic;
import com.nilesh.kafkastreams.parsers.YAMLParser;
import com.nilesh.kafkastreams.prepare.container.KafkaCluster;
import com.nilesh.kafkastreams.prepare.message.MessageProcessor;
import com.nilesh.kafkastreams.prepare.message.beans.Messages;
import com.nilesh.kafkastreams.prepare.message.parser.MessageParser;
import com.nilesh.kafkastreams.prepare.producer.ProducerHelper;
import com.nilesh.kafkastreams.streams.PrintRecordMetadataStreams;
import com.nilesh.kafkastreams.helper.KafkaAdmin;
import com.nilesh.kafkastreams.helper.Producer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

public class PrintRecordMetadataStreamingExample {

    private KafkaConfig kafkaConfig;
    private KafkaCluster kafkaCluster;

    public static void main(String[] args) throws IOException {
        PrintRecordMetadataStreamingExample printStreamingExample = new PrintRecordMetadataStreamingExample();
        printStreamingExample.setup();
        printStreamingExample.test();
        // streamingSimple.tearDown();
    }

    public void setup() {
        System.out.println("Streaming simple setup() called");
        KafkaYamlConfiguration kafkaYamlConfiguration = new YAMLParser<KafkaYamlConfiguration>().load(KafkaYamlConfiguration.class);
        kafkaConfig = kafkaYamlConfiguration.getKafkaConfigs().stream().filter(kb -> kb.getType().equalsIgnoreCase("default")).findFirst().get();
        kafkaCluster = new KafkaCluster();
        kafkaCluster.setup(kafkaConfig);
    }

    public void tearDown() {
        kafkaCluster.tearDown();
    }

    public void test() throws IOException {
        String bootStrapServers = kafkaConfig.getBootStrapServers();
        System.out.println(bootStrapServers);
        // admin related, like topic creation
        KafkaAdmin kafkaAdmin = new KafkaAdmin(bootStrapServers);
        for (Topic topic : kafkaConfig.getTopics()) {
            kafkaAdmin.createTopic(topic.getName(), topic.getPartitions(), topic.getReplication());
        }
        System.out.println("After creating topics");
        // write producer code
        ProducerHelper producerHelper = new ProducerHelper();
        Properties producerProps = producerHelper.buildProperties(kafkaConfig.getProducerConfigs(), bootStrapServers);
        MessageParser messageParser = new MessageParser();
        Messages messages = messageParser.load("/messages.json");
        MessageProcessor messageProcessor = new MessageProcessor(messages);
        Producer producer = new Producer(producerProps);
        producer.produce(messageProcessor);
        System.out.println("Produced successfully");
        // Consumer consumer = new Consumer();
        // consumer.consume("ONE", bootStrapServers);
        // producer.produce("ONE", "one", "one");
        // write consumer code
        Collection<String> topics = new ArrayList<>();
        messages.getMessages()
                .forEach(message -> topics.addAll(Arrays.asList(message.getTopics().split(","))));
        System.out.println(topics);
        new PrintRecordMetadataStreams().streamsConsumer(bootStrapServers, topics);
        // streamingSimple.tearDown();
        // System.out.println("Tear down called");
    }
}


