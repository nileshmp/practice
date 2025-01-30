package com.nilesh.kafkastreams.streams.tutorials;

import com.nilesh.kafkastreams.streams.Constants;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import java.io.IOException;
import java.util.Objects;

public class BasicStreamsWithStoreExample {
    public static void main(String[] args) throws IOException {
        BasicStreamsWithStoreExample basicStreamsWithStoreExample = new BasicStreamsWithStoreExample();
        String applicationName = "basic-example-with-store-example-3";
        Init.create_and_populate_topics();
        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream = builder.stream(Constants.inputTopic, Consumed.with(Serdes.String(), Serdes.String()));
        StoreBuilder<KeyValueStore<String, String>> storeBuilder = basicStreamsWithStoreExample.buildStore(applicationName);
        builder.addStateStore(storeBuilder);
        stream.peek((k, s) -> System.out.printf("After keying: %s, value: %s\n", k, s))
                .transform(new SampleTransformSupplier(storeBuilder.name()), storeBuilder.name())
                .peek((k, s) -> System.out.printf("After transform: %s, value: %s\n", k, s));
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), Configs.buildStatefulStreamProperties(applicationName));
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
    }

    private StoreBuilder<KeyValueStore<String, String>> buildStore(String applicationName) {
        // Define the state store
        return Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore(applicationName + "-state-store"),
                Serdes.String(), Serdes.String());
    }


    private static class SampleTransformSupplier implements TransformerSupplier<String, String, KeyValue<String, String>> {

        final private String stateStoreName;

        public SampleTransformSupplier(String stateStoreName) {
            this.stateStoreName = stateStoreName;
        }

        @Override
        public Transformer<String, String, KeyValue<String, String>> get() {
            return new Transformer<String, String, KeyValue<String, String>>() {
                private KeyValueStore<String, String> stateStore;

                @SuppressWarnings("unchecked")
                @Override
                public void init(ProcessorContext processorContext) {
                    stateStore = (KeyValueStore<String, String>) processorContext.getStateStore(stateStoreName);
                }

                @Override
                public KeyValue<String, String> transform(String key, String value) {
                    String storedCount = stateStore.get(key);
                    Long countSoFar = Objects.isNull(storedCount) ? 0L : Long.parseLong(storedCount);
                    countSoFar += value.length();
                    System.out.printf(" Key: %s, Value: %s, Count: %d\n\n", key, value, countSoFar);
                    stateStore.put(key, String.valueOf(countSoFar));
                    return KeyValue.pair(key, value);
                }

                @Override
                public void close() {
                    // No need to close as this is handled by kafka.
                }
            };
        }
    }
}
