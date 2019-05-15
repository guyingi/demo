package gu;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Produce {

    public void doProduce(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "143.3.119.164:6667,143.3.119.165:6667,143.3.119.167:6667");
        props.put("acks", "all");
        props.put("delivery.timeout.ms", 30000);
        props.put("batch.size", 10);
        props.put("linger.ms", 1);
        props.put("request.timeout.ms", 29999);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", gu.MyPartition.class);
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 10; i < 20; i++)
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));

        producer.flush();
        producer.close();

    }

    public static void main(String[] args) {
        new Produce().doProduce();
        System.out.println("run over");
    }
}
