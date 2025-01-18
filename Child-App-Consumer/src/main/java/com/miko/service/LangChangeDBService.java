package com.miko.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

@Service
public class LangChangeDBService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	 

    // Insert language change acknowledgment into DB
    public void insertAcknowledgment(String userId, String language, String status) {
        String sql = "INSERT INTO language_change_acknowledgments (user_id, language, status) VALUES (?, ?, ?)";
        
        int i=jdbcTemplate.update(sql, userId, language, status);
        
        if(i==1) {
        	System.out.println("Data insetted into database successfully!!");
        }
    }

    
   // Table:
   // 	CREATE TABLE language_change_acknowledgments(user_id VARCHAR(255) PRIMARY KEY, status VARCHAR(255),
   // 			   language VARCHAR(255));

}
