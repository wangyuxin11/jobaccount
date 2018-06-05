package com.wanda.jobaccount.kafka.spring;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


/**
 * 使用producer发送完消息可以通过2.5中提到的服务器端消费者监听到消息。也可以使用接下来介绍的java消费者程序来消费消息
 * 
 * @author wangyuxin11
 *
 */
public class Test2Producer {

	public static void main(String[] args){
	    Properties props = new Properties();
//	    props.put("bootstrap.servers", "192.168.180.128:9092");
	    props.put("bootstrap.servers", "10.213.129.58:9092,10.213.129.59:9092,10.213.129.60:9092");
	    props.put("acks", "all");
	    props.put("retries", 0);
	    props.put("batch.size", 16384);
	    props.put("linger.ms", 1);
	    props.put("buffer.memory", 33554432);
	    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

	    Producer<String, String> producer = new KafkaProducer<String, String>(props);
	    for (int i = 0; i < 100; i++)
	        producer.send(new ProducerRecord<String, String>("topic-test", Integer.toString(i), Integer.toString(i)));

	    producer.close();

	}
	
	
}
