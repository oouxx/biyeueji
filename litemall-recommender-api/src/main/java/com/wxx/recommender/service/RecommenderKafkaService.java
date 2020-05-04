package com.wxx.recommender.service;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class RecommenderKafkaService {
    private final static String TOPIC = "recommender";
    private final static String KEY = "rating";
    private final static Logger logger = LoggerFactory.getLogger(RecommenderKafkaService.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;
    public String recommenderKafkaProduce(String message){
        ListenableFuture<SendResult<String,String>> future=kafkaTemplate.send(TOPIC , KEY ,message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                logger.info(TOPIC+" - 生产者 发送消息失败："+throwable.getMessage());
            }
            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                //成功的处理
                logger.info(TOPIC+" - 生产者 发送消息成功："+stringObjectSendResult.toString());
            }
        });
        return "";

    }

    public void main(String message) throws Exception {
        String topic = "recommender";

        Properties props = new Properties();
        props.put("bootstrap.servers",
                "47.93.97.16:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("acks", "all");
//        props.put("retries", 1);

        // create an instance of producer
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        // asynchronously & future
        // Send the given record asynchronously and return a future which will
        // eventually contain the response information

        Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topic, message));
        RecordMetadata recMeta = future.get();
        System.out.println(
                "record meta " + " " + ":  offset=" + recMeta.offset() + ", partition=" + recMeta.partition());

        // asynchronously & callback
//        producer.send(new ProducerRecord<String, String>(topic, "World"), (metadata, e) -> {
//            if (e != null) {
//                e.printStackTrace();
//            } else {
//                System.out.println("metadata: offset=" + metadata.offset() + ", partition=" + metadata.partition());
//            }
//        });

        // Flush any accumulated records from the producer.
        // Blocks until all sends are complete.
        producer.flush();
        producer.close();
    }
}
