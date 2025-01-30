package com.nilesh.kafkastreams.prepare.messages;

import com.nilesh.kafkastreams.prepare.message.beans.Message;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public class TopicProcessor {
    private final Message message;
    private final String[] topics;
    private int invokeCount;

    public TopicProcessor(Message message) {
        this.message = message;
        topics = message.getTopics().split(",");
        invokeCount = -1;
    }

    public Iterator<String> next() {
        invokeCount++;
        String mix = message.getMix();
        // duplicate, random, alternate
        if (mix.equalsIgnoreCase("duplicate")) {
            return Arrays.stream(topics).iterator();
        } else if (mix.equalsIgnoreCase("alternate")) {
            return Stream.of(topics[invokeCount%topics.length]).iterator();
        } else if (mix.equalsIgnoreCase("random")) {
            int min = 0;
            int max = topics.length - 1;
            int index = new Random().nextInt(max - min + 1) + min;
            return Stream.of(topics[index]).iterator();
        }
        return null;
    }
}
