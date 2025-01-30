package com.nilesh.kafkastreams.prepare.message;

import com.nilesh.kafkastreams.prepare.message.beans.Message;
import com.nilesh.kafkastreams.prepare.message.beans.Messages;
import com.nilesh.kafkastreams.prepare.messages.TopicProcessor;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Iterator;
import java.util.List;

public class MessageProcessor {

    private final Messages messages;

    public MessageProcessor(Messages messages) {
        this.messages = messages;
    }

    public void process(Producer<?, ?> kafkaProducer) {
        List<Message> messageList = this.messages.getMessages();
        for (Message message : messageList) {
            int iterate = message.getIterate();
            // handle cases where iterate is null or 0
            TopicProcessor topicProcessor = new TopicProcessor(message);
            Message.Key key = message.getKey();
            Message.Value value = message.getValue();
            String keyType = key.getType();
            Class<?> keyKlass = null;
            if(keyType.equalsIgnoreCase("int")) {
                keyKlass = Integer.class;
            }
            String valueType = value.getType();
            Class<?> valueKlass = null;
            if(valueType.equalsIgnoreCase("string")) {
                valueKlass = String.class;
            }
            String keyValue = key.getValue();
            String valueValue = value.getValue();
            if(iterate > 0) {
                for (int count = 0; count < iterate; count++) {
                    // move this to custom method
                    String tempKey = keyValue;
                    tempKey = tempKey.replace("${iterate.count}", String.valueOf(count));
                    String tempValue = valueValue;
                    tempValue = tempValue.replace("${iterate.count}", String.valueOf(count));
                    Iterator<String> topicsIterator = topicProcessor.next();
                    while(topicsIterator.hasNext()) {
                        String topic = topicsIterator.next();
                        ProducerRecord producerRecord = new ProducerRecord<>(topic, tryCast(keyKlass, tempKey), tryCast(valueKlass, tempValue));
                        kafkaProducer.send(producerRecord);
                    }
                }
            }
        }
    }

    private Object tryCast(Class<?> clazz, String value)
    {
        if(clazz.getName().equalsIgnoreCase("java.lang.Integer"))
            return Integer.parseInt(value);
        else if(clazz.getName().equalsIgnoreCase("java.lang.String"))
            return value;
        else
            return value;
    }
}
