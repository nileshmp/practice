package com.nilesh.kafkastreams.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.processor.api.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

public class PrintRecordMetadataStreams {

    private static final String APPLICATION_ID = "application-reset-demo";
    private static final String CLIENT_ID = "application-reset-demo-client";

    public static void main(String[] args) {

    }

    public void streamsConsumer(String bootStrapServers, Collection<String> topics) {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "print_stream_id-3");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final StreamsBuilder builder = new StreamsBuilder();
        // builder.stream("")
        //         .foreach((key, value) -> System.out.println("---------------------  Key: " + key + ", Value: " + value));
        Topology topology = builder.build();
        topology.addSource("source", topics.toArray(new String[0]));
        topology.addProcessor("process", new SimplProcessorSupplier(), "source");
        KafkaStreams kafkaStreams = new KafkaStreams(topology, streamsConfiguration);
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
    }
}

class SimplProcessorSupplier implements ProcessorSupplier<Integer, String, Integer, String> {

    public SimplProcessorSupplier() {
    }

    @Override
    public Processor<Integer, String, Integer, String> get() {
        return new SimplProcessor();
    }

    private class SimplProcessor implements Processor<Integer, String, Integer, String> {

        private ProcessorContext<Integer, String> context;

        @Override
        public void init(ProcessorContext<Integer, String> context) {
            this.context = context;
        }

        @Override
        public void process(Record<Integer, String> record) {
            Optional<RecordMetadata> optionalRecordMetadata = this.context.recordMetadata();
            if(optionalRecordMetadata.isPresent()) {
                RecordMetadata recordMetadata = optionalRecordMetadata.get();
                System.out.printf("Timestamp: %d %n", record.timestamp());
                System.out.printf("Topic: %s, Key: %d, Value:%s, Offset:%d, Partition: %d%n", recordMetadata.topic(), record.key(), record.value(),recordMetadata.offset(), recordMetadata.partition());
            }
        }
    }
}