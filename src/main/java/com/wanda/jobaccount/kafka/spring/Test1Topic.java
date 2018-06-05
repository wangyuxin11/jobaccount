package com.wanda.jobaccount.kafka.spring;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

/**
 * 使用AdminClient API可以来控制对kafka服务器进行配置，我们这里使用
 * NewTopic(String name, int numPartitions, short replicationFactor)的构造方法来创建了一个名为“topic-test”，
 * 分区数为1，复制因子为1的Topic.
 * 
 * @author wangyuxin11
 *
 */
public class Test1Topic {
	
	public static void main(String[] args) {
	    //创建topic
	    Properties props = new Properties();
	    //props.put("bootstrap.servers", "192.168.180.128:9092");
	    props.put("bootstrap.servers", "10.213.129.58:9092,10.213.129.59:9092,10.213.129.60:9092");
	    AdminClient adminClient = AdminClient.create(props);
	    ArrayList<NewTopic> topics = new ArrayList<NewTopic>();
	    NewTopic newTopic = new NewTopic("topic-test", 1, (short) 1);
	    topics.add(newTopic);
	    CreateTopicsResult result = adminClient.createTopics(topics);
	    try {
	        result.all().get();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    } catch (ExecutionException e) {
	        e.printStackTrace();
	    }
	}

}
