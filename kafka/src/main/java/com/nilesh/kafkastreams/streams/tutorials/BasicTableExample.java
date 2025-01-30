package com.nilesh.kafkastreams.streams.tutorials;

import com.nilesh.kafkastreams.streams.Constants;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

public class BasicTableExample {
    public static void main(String[] args) {
        String applicationName = "basic-table-example-1";
        BasicTableExample basicTableExample = new BasicTableExample();
        final StreamsBuilder streamsBuilder = new StreamsBuilder();
        KTable<String, String> table = streamsBuilder.table(Constants.inputTopic);
        StoreBuilder<KeyValueStore<String, String>> keyValueStoreStoreBuilder = basicTableExample.buildStore(applicationName);
        streamsBuilder.addStateStore(keyValueStoreStoreBuilder);
        table
                .toStream()
                .peek((key, value) -> System.out.println(">>>>>>>>>>>>>>>>>>  Key: " + key + ", value: " + value));
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), Configs.buildTableProperties(applicationName));
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
    }

    private StoreBuilder<KeyValueStore<String, String>> buildStore(String applicationName) {
        // Define the state store
        return Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore(applicationName + "-state-store"),
                Serdes.String(), Serdes.String());
    }
}
