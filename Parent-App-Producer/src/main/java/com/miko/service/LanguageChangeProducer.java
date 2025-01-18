package com.miko.service;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class LanguageChangeProducer {

    private final String TOPIC = "miko3-language-change";

    private KafkaProducer<String, String> producer;

	/*
	 * public LanguageChangeProducer() { // Kafka producer configuration Properties
	 * properties = new Properties();
	 * properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	 * properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	 * properties.put(ProducerConfig.RETRIES_CONFIG, 3);
	 * properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	 * StringSerializer.class.getName());
	 * 
	 * 
	 * 
	 * producer = new KafkaProducer<>(properties); }
	 */
    
    
    
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public LanguageChangeProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    
    // Method to send the language change message
    public void sendLanguageChangeNotification(String userId, String language) {
    	
    	
        try {
			String message = String.format("{\"userId\":\"%s\", \"language\":\"%s\"}", userId, language);
			kafkaTemplate.send(TOPIC, userId, message);
			
			System.out.println("message sent to the topic successfully!!!");
		} catch (Exception e) {
			
			System.out.println("message not sent to the topic!!!");
		}
    }

}
