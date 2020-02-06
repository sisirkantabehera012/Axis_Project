package com.jms.activemq.config;



import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import com.jms.activemq.sender.Producer;
/**
 * This class is responsible for creating those object which are not comes through auto configuration
 * @author RTPL
 *
 */
@Configuration
@EnableJms
public class JMSConfig {
    
	//creating a logger for logging
	private static Logger logger=LoggerFactory.getLogger(Producer.class);
	
	
	/**
	 * This variable read username from application.properties file
	 */
	@Value("${spring.activemq.user}")
	private String username;
	
	
	/**
	 * This variable read pazzword from application.properties file
	 */
	@Value("${spring.activemq.password}")
	private String pazzword;
	/**
	 * this method creates one topic i.e News
	 * @return
	 */
	@Bean
	public Topic createTopic() {
		logger.info("topic created successfully");
		return new ActiveMQTopic("News");
	}
	/**
	 * this method creates a connection factory
	 * @return ActiveMQConnectionFactory
	 */
	 @Bean
	public ActiveMQConnectionFactory connectionFatory(){
	    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
	    factory.setUserName(username);
	    factory.setPassword(pazzword);
	    logger.info("ActiveMQConnectionFactory successfully created");
	    return factory;
	}

    /**
     *  This method create JmsTemplate object and return that object
     * @return JmsTemplate
     */
	@Bean
	public JmsTemplate jmsTemplate(){
		logger.info("JmsTemplate successfully created");
	    return new JmsTemplate(connectionFatory());
	}
    
}
