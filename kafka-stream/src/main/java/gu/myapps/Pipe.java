package gu.myapps;

import gu.configuration.SystemConfiguration;
import gu.domain.Work;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author gu
 * @date 2019/4/1 9:46
 */
public class Pipe {
    public static void main(String[] args) throws Exception {
        new Work().doOcrWork();
    }


    public static void run2(){
//        Properties props = new Properties();
//        final StreamsBuilder builder = new StreamsBuilder();
//        KStream<String, String> source = builder.stream("streams-plaintext-input");
//        KStream<String, String> words = source.flatMapValues(new ValueMapper<String, Iterable<String>>() {
//            @Override
//            public Iterable<String> apply(String value) {
//                return Arrays.asList(value.split("\\W+"));
//            }
//        });
//        words.to("streams-pipe-output");
//
//        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
//        // attach shutdown handler to catch control-c
//        final CountDownLatch latch = new CountDownLatch(1);
//        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
//            @Override
//            public void run() {
//                streams.close();
//                latch.countDown();
//            }
//        });
//
//        try {
//            streams.start();
//            latch.await();
//        } catch (Throwable e) {
//            System.exit(1);
//        }
    }

    public static void run1(){
//        final StreamsBuilder builder = new StreamsBuilder();
//        builder.stream("streams-plaintext-input").to("streams-pipe-output");
//        final Topology topology = builder.build();
//        final KafkaStreams streams = new KafkaStreams(topology, props);
//        final CountDownLatch latch = new CountDownLatch(1);
//
//        // attach shutdown handler to catch control-c
//        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
//            @Override
//            public void run() {
//                streams.close();
//                latch.countDown();
//            }
//        });
//
//        try {
//            streams.start();
//            latch.await();
//        } catch (Throwable e) {
//            System.exit(1);
//        }
    }
}
