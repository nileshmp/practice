package com.nilesh.kafkastreams.streams.extractor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class MyEventTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(final ConsumerRecord<Object, Object> record, final long previousTimestamp) {
        long timestamp = 1727257142927L;
        int key = (int) record.key();
        // for the pur pose of experimenting we will print the values in reverse order.
        System.out.printf("Inside Timestamp extractor: Topic:%s, offset: %d \n", record.topic(), record.offset());
        if(record.topic().equalsIgnoreCase("ONE")) {
            long newTimeStamp = (timestamp + (key * 2L) +1);
            System.out.println("Assigned timestamp : " + newTimeStamp);
            return newTimeStamp;
        } else {
            long newTimeStamp = (timestamp+(key * 2L)+2);
            System.out.println("Assigned timestamp : " + newTimeStamp);
            return newTimeStamp;
        }
    }

    public static void main(String[] args) {
        for(int count =0;count<100;count++){
            System.out.println((count * 2) + 1);
            System.out.println((count * 2) + 2);
        }
    }
}
