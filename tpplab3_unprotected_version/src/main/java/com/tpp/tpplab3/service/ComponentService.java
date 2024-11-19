package com.tpp.tpplab3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ComponentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllComponents() {
        return jdbcTemplate.queryForList("SELECT * FROM Components");
    }

    public Map<String, Object> findById(int id) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM Components WHERE componentId = ?", id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void addComponent(Map<String, String> componentData) {
        jdbcTemplate.update("INSERT INTO Components (name, type, cost, description) VALUES (?, ?, ?, ?)",
                componentData.get("name"),
                componentData.get("type"),
                componentData.get("cost"),
                componentData.get("description"));
    }

    public void updateComponent(Map<String, String> componentData) {
        jdbcTemplate.update("UPDATE Components SET name = ?, type = ?, cost = ?, description = ? WHERE componentId = ?",
                componentData.get("name"),
                componentData.get("type"),
                componentData.get("cost"),
                componentData.get("description"),
                componentData.get("componentId"));
    }

    public void deleteComponentById(int id) {
        jdbcTemplate.update("DELETE FROM Components WHERE componentId = ?", id);
    }
}
