package com.miko.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LanChangeDatabase {
	
	
    private final JdbcTemplate jdbcTemplate;

    public LanChangeDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Query the acknowledgment status for a given user
    @SuppressWarnings("deprecation")
	public String getAcknowledgmentStatus(String userId) {
        String sql = "SELECT status FROM language_change_acknowledgments WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
    }
}
