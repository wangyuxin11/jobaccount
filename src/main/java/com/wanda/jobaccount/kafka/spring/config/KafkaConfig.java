package com.wanda.jobaccount.kafka.spring.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.wanda.jobaccount.kafka.spring.SimpleConsumerListener;


/**
 * 
 * https://www.cnblogs.com/hei12138/p/7805475.html
 *
 */



@Configuration
@EnableKafka
public class KafkaConfig {

	//topic config Topic的配置开始
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<String, Object>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.180.128:9092");
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"10.213.129.58:9092,10.213.129.59:9092,10.213.129.60:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("foo", 10, (short) 2);
    }
    //topic的配置结束
    
    //配置生产者Factort及Template
  //producer config start
      @Bean
      public ProducerFactory<Integer, String> producerFactory() {
          return new DefaultKafkaProducerFactory<Integer,String>(producerConfigs());
      }
      @Bean
      public Map<String, Object> producerConfigs() {
          Map<String, Object> props = new HashMap<String,Object>();
//          props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.180.128:9092");
          props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"10.213.129.58:9092,10.213.129.59:9092,10.213.129.60:9092");
          props.put("acks", "all");
          props.put("retries", 0);
          props.put("batch.size", 16384);
          props.put("linger.ms", 1);
          props.put("buffer.memory", 33554432);
          props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
          props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
          return props;
      }
      @Bean
      public KafkaTemplate<Integer, String> kafkaTemplate() {
          return new KafkaTemplate<Integer, String>(producerFactory());
      }
  //producer config end
      
     // 配置ConsumerFactory
    //consumer config start
        @Bean
        public ConcurrentKafkaListenerContainerFactory<Integer,String> kafkaListenerContainerFactory(){
            ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<Integer, String>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public ConsumerFactory<Integer,String> consumerFactory(){
            return new DefaultKafkaConsumerFactory<Integer, String>(consumerConfigs());
        }


        @Bean
        public Map<String,Object> consumerConfigs(){
            HashMap<String, Object> props = new HashMap<String, Object>();
            //props.put("bootstrap.servers", "192.168.180.128:9092");
            props.put("bootstrap.servers", "10.213.129.58:9092,10.213.129.59:9092,10.213.129.60:9092");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            return props;
        }
    //consumer config end
        
        //我们同时也需要将这个类作为一个Bean配置到KafkaConfig中
		@Bean
		public SimpleConsumerListener simpleConsumerListener(){
		   return new SimpleConsumerListener();
		}
		//默认spring-kafka会为每一个监听方法创建一个线程来向kafka服务器拉取消息
}
