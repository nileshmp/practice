package com.nilesh.kafkastreams.helper;

import static com.nilesh.kafkastreams.streams.Constants.BOOT_STRAP_SERVERS;
import static com.nilesh.kafkastreams.streams.Constants.inputTopic;

import com.nilesh.kafkastreams.prepare.message.MessageProcessor;
import java.io.IOException;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer<T, V> {

    private final org.apache.kafka.clients.producer.Producer<T, V> kafkaProducer;

    public static void main(String[] args) throws IOException {
        // String fileName = "/Users/nilesh/work/codebase/kafka-streams/src/main/resources/input.txt";
        Producer producer = new Producer<String, String>(buildProperties());
        producer.produce(inputTopic, 10099);
        //        producer.produceFromFile(fileName, KafkaAdmin.TOPIC_DEMO);
    }

    public Producer(Properties properties) {
        kafkaProducer = new KafkaProducer<>(properties);
    }

    public void produce(String inputTopic, int messageCount) throws IOException {
        Producer producer = new Producer(buildProperties());
        for (int i = 1; i < messageCount; i++) {
            producer.produce(inputTopic, String.valueOf(i), String.valueOf(i + 1));
        }
    }

    public void produceFromFile(String filename, String inputTopic) throws IOException {
        FileReader fileReader = new FileReader(filename);
        Producer producer = new Producer<T, V>(buildProperties());
        while (fileReader.hasNext()) {
            String value = fileReader.next();
            System.out.println("Value was : " + value);
            producer.produce(inputTopic, value, value);
        }
        producer.close();
    }

    public void produce(String topic, T key, V message) throws IOException {
        System.out.println("Value was : " + message);
        kafkaProducer.send(new ProducerRecord<T, V>(topic, key, message));
    }

    public static Properties buildProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOT_STRAP_SERVERS);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public void close() {
        kafkaProducer.close();
    }

    public void produce(MessageProcessor messageProcessor) {
        messageProcessor.process(kafkaProducer);
    }
}