package com.nilesh.kafkastreams.springboot.config;

import java.util.HashMap;
import java.util.Map;

import com.nilesh.kafkastreams.springboot.service.KStreamProcessor;
import com.nilesh.kafkastreams.springboot.service.KTableProcessor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import static org.apache.kafka.streams.StreamsConfig.*;

@EnableKafka
@EnableKafkaStreams
@Configuration
public class KafkaStreamsConfig {

    @Value(value = "${spring.kafka.bootstrap-server}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.topic.demo}")
    private String inputTopic;

    @Value(value = "${kafka.streams.state.dir}")
    private String stateDir;

    @Value(value = "${streams.app.id}")
    private String streamsAppId;

    @Value(value = "${consumer.factory.id}")
    private String factoryId;

    @Autowired
    private KStreamProcessor kstreamProcessor;

    @Autowired
    private KTableProcessor ktableProcessor;

    private static final Logger logger = LoggerFactory.getLogger(KafkaStreamsConfig.class);

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        logger.debug("Initializing kStreamsConfig...");
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, streamsAppId);
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(STATE_DIR_CONFIG, stateDir);

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        logger.debug("Initializing consumerFactory...");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, factoryId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {
        logger.debug("Initializing kafkaListenerContainerFactory...");
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KStream<String, DataModel> kStream(StreamsBuilder kStreamBuilder) {
        logger.debug("Initializing streams...");
        KStream<String, DataModel> stream = kStreamBuilder.stream(inputTopic, Consumed.with(Serdes.String(), new DataModelSerde()));
        //Process KStream
        this.kstreamProcessor.process(stream);
        //Process KTable
        this.ktableProcessor.process(stream);
        return stream;
    }
}