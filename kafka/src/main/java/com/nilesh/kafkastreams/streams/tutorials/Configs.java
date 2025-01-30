package com.nilesh.kafkastreams.streams.tutorials;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

import static com.nilesh.kafkastreams.streams.Constants.BOOT_STRAP_SERVERS;

public class Configs {
    public static Properties buildStreamProperties(String name)
    {
        java.util.Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, name );
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, name + "-client-id");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVERS);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return streamsConfiguration;
    }

    public static Properties buildStatefulStreamProperties(String name)
    {
        java.util.Properties streamsConfiguration = buildStreamProperties(name);
        streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG,"/Users/nilesh/work/codebase/kafka-streams/stream-state");
        return streamsConfiguration;
    }

    public static Properties buildTableProperties(String name)
    {
        java.util.Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, name);
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, name + "-client-id");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVERS);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, "/Users/nilesh/work/codebase/kafka-streams/stream-state");
        return streamsConfiguration;
    }
}
