package com.wanda.jobaccount.kafka.spring;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 这里我们使用Consume API 来创建了一个普通的java消费者程序来监听名为“topic-test”的Topic，
 * 每当有生产者向kafka服务器发送消息，我们的消费者就能收到发送的消息。
 * 
 */
public class Test3Consumer {

	
	public static void main(String[] args){
	    Properties props = new Properties();
	    //props.put("bootstrap.servers", "192.168.12.65:9092");
	    props.put("bootstrap.servers", "10.213.129.58:9092,10.213.129.59:9092,10.213.129.60:9092");
	    props.put("group.id", "test");
	    props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    final KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
	    consumer.subscribe(Arrays.asList("topic-test"), new ConsumerRebalanceListener() {
	    	@Override
	    	public void onPartitionsAssigned(Collection<org.apache.kafka.common.TopicPartition> collection) {
	    		//将偏移设置到最开始
	            consumer.seekToBeginning(collection);
	    	}

			@Override
			public void onPartitionsRevoked(Collection<org.apache.kafka.common.TopicPartition> collection) {
				// TODO Auto-generated method stub
				
			}
	    });
	    while (true) {
	        ConsumerRecords<String, String> records = consumer.poll(100);
	        for (ConsumerRecord<String, String> record : records)
	            System.out.printf("****&&&&####——————offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	    }
	}
	
	
	
}
