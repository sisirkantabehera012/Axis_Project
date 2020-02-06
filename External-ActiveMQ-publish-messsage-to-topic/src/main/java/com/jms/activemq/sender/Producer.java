package com.jms.activemq.sender;

import java.time.Instant;
import java.util.stream.IntStream;

import javax.jms.DeliveryMode;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class defining a method for publishing message
 * 
 * @author RTPL
 *
 */
@RestController
public class Producer {

	private static Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Topic topic;

	/**
	 * This method exposing a path for publishing message
	 * 
	 * @param msg
	 * @return String
	 */
	@GetMapping("publish/{msg}")
	public String publish(@PathVariable("msg") final String msg) {
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
		jmsTemplate.setDeliveryPersistent(true);
		
		 // calculate elapsed time in milli seconds
        Long startTime = Instant.now().toEpochMilli();
		
		IntStream.rangeClosed(1, 100).parallel().forEach(val -> {
		jmsTemplate.convertAndSend(topic, msg);
		logger.info("Message published successfully to a topic");
		});
		Long endTime = Instant.now().toEpochMilli();
		
		System.out.println("Total time in millisec "+(endTime-startTime)+"ms");
		return "Your message <b>" + msg + "</b> published successfully";

	}
}
