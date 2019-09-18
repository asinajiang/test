package com.rumwei.func.kafkatest;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaTest {

    private static final String topic = "rumwei";

    public static void main(String[] args){


//        //生产数据
//        Properties prop = new Properties();
//        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(prop);
//
//        ProducerRecord<String,String> pr = new ProducerRecord<String, String>(topic,"keyZoo","valueOnly");
//        producer.send(pr, new Callback() {
//            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                System.out.println();
//            }
//        });
//        producer.close();
//
//        Properties propc = new Properties();
//        propc.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        propc.put(ConsumerConfig.GROUP_ID_CONFIG,"0");
//        propc.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        propc.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        propc.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
//        propc.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
//        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(propc);
//        consumer.subscribe(Arrays.asList(topic));
//        ConsumerRecords<String,String> consumerRecord = consumer.poll(Duration.ofMillis(200));
//        try{
//            Thread.sleep(300);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        for (ConsumerRecord<String,String> ele : consumerRecord){
//            System.out.println(ele);
//        }

        Properties propc = new Properties();
        propc.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.196:9092");
        propc.put(ConsumerConfig.GROUP_ID_CONFIG,"0");
        propc.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        propc.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        propc.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        propc.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(propc);
        consumer.subscribe(Arrays.asList("test"));
        while (true){
            ConsumerRecords<String,String> consumerRecord = consumer.poll(Duration.ofMillis(10));
            try{
                Thread.sleep(11);
            }catch(Exception e){
                e.printStackTrace();
            }
            for (ConsumerRecord<String,String> ele : consumerRecord){
                System.out.println(ele);
            }
        }




    }
}
