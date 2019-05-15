package gu;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Comsume {

    public static void doConsume(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "143.3.119.164:6667,143.3.119.165:6667,143.3.119.167:6667");
        props.put("group.id", "group1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
//        TopicPartition p = new TopicPartition("test",0);
//        consumer.assign(Arrays.asList(p));
////        consumer.assign(Arrays.asList(p));
//        consumer.seekToBeginning(Arrays.asList(p));
//        consumer.seek(p,0);]

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
//            consumer.
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("partition = %s, offset = %d, key = %s, value = %s%n", record.partition(),record.offset(), record.key(), record.value());
        }
    }

    public static void main(String[] args) {
        doConsume();
        System.out.println("run over");
    }
}
