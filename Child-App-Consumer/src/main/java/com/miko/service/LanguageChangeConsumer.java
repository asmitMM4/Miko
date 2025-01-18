package com.miko.service;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;

@Service
public class LanguageChangeConsumer {

    private KafkaConsumer<String, String> consumer;
    private final String TOPIC = "miko3-language-change";
    private final String GROUP_ID = "miko3-language-group";
    
    @Autowired
    LangChangeDBService service;

    public LanguageChangeConsumer() {
        // Kafka consumer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", GROUP_ID);
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("auto.offset.reset", "earliest");

        consumer = new KafkaConsumer<>(properties);
    }


    
    @KafkaListener(topics = "miko3-language-change", groupId = "miko3-language-group")
    public void listenForLanguageChange(String message) {
        System.out.println("Received language change request: " + message);

        // Process language change and store the acknowledgment in DB
        String userId = extractUserIdFromMessage(message);
        String language = extractLanguageFromMessage(message);
        boolean success = processLanguageChangeForUser(userId, language);

        // Update acknowledgment status in DB
        String status = success ? "success" : "failure";
        service.insertAcknowledgment(userId, language, status);
    }
    
    
    // sample message:: {\"userId\":\"%s\", \"language\":\"%s\"}
    private String extractUserIdFromMessage(String message) {
        // Extract userId from the message (JSON parsing)
        return message.split(",")[0].split(":")[1].replace("\"", "").trim();
    }
    
    private String extractLanguageFromMessage(String message) {
        // Extract language from the message (JSON parsing)
        return message.split(",")[1].split(":")[1].replace("\"", "").replace("}", "").trim();
    }
    
    private boolean processLanguageChangeForUser(String userId, String language) {
        // Logic to change language
        // For now, we'll assume the change is successful
        return true;
    }
    
    
    @SuppressWarnings("deprecation")
	public void startListening() {
        consumer.subscribe(Collections.singletonList("miko3-language-change"));
        while (true) {
            consumer.poll(100).forEach(record -> listenForLanguageChange(record.value()));
        }
    }

}
