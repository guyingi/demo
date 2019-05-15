package gu.domain;

import gu.configuration.SystemConfiguration;
import gu.pojo.InputMessage;
import gu.pojo.OutputMessage;
import gu.service.PicToTextService;
import gu.service.impl.PicToTextServiceImpl;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author gu
 * @date 2019/4/1 16:47
 */
public class Work {

    SystemConfiguration systemConfiguration = new SystemConfiguration();
    PicToTextService picToTextService = new PicToTextServiceImpl();

    public void route(String type){
        if(type.equals("ocr")){
            doOcrWork();
        }else if(type.equals("extract")){

        }
    }

    public void doOcrWork(){
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, systemConfiguration.getApplicationId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, systemConfiguration.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        System.out.println(systemConfiguration.toString());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(systemConfiguration.getInputTopic());
        KStream<String, String> result = source.mapValues(value -> {
            InputMessage inputMessage = picToTextService.convertRecordToMessage(value);
            OutputMessage outputMessage = picToTextService.doParse(inputMessage);
            return outputMessage.toString();
        });
        result.to(systemConfiguration.getOutputTopic(), Produced.with(Serdes.String(), Serdes.String()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        // attach shutdown handler to catch control-c
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }

    public void doExtractWork(){
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "extract-1");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, systemConfiguration.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        System.out.println(systemConfiguration.toString());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(systemConfiguration.getInputTopic());
        KStream<String, String> result = source.mapValues(value -> {
            InputMessage inputMessage = picToTextService.convertRecordToMessage(value);
            OutputMessage outputMessage = picToTextService.doParse(inputMessage);
            return outputMessage.toString();
        });
        result.to(systemConfiguration.getOutputTopic(), Produced.with(Serdes.String(), Serdes.String()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        // attach shutdown handler to catch control-c
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}



