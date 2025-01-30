package com.nilesh.kafkastreams.parsers;

import static com.nilesh.kafkastreams.constants.Constants.KAFKA_YAML_CONFIG;

import java.io.InputStream;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YAMLParser<T> {

    public T load(Class<T> clazz) {
        InputStream inputStream = this.getClass().getResourceAsStream(KAFKA_YAML_CONFIG);
        Yaml yaml = new Yaml(new Constructor(clazz, new LoaderOptions()));
        return yaml.load(inputStream);
    }
}
