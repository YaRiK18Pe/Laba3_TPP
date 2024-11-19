package com.tpp.tpplab3.controllers;

import com.tpp.tpplab3.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String listComponents(Model model) {
        model.addAttribute("components", componentService.getAllComponents());
        return "components";
    }

    @GetMapping("/add")
    public String addComponentForm(Model model) {
        model.addAttribute("component", Map.of());
        return "add-component";
    }

    @PostMapping("/add")
    public String addComponent(@RequestParam Map<String, String> componentData) {
        componentService.addComponent(componentData);
        return "redirect:/components";
    }

    @GetMapping("/edit/{id}")
    public String editComponentForm(@PathVariable("id") int id, Model model) {
        Map<String, Object> component = componentService.findById(id);
        if (component != null) {
            model.addAttribute("component", component);
            return "edit-component";
        } else {
            return "redirect:/components";
        }
    }

    @PostMapping("/update/{id}")
    public String updateComponent(@PathVariable("id") int id, @RequestParam Map<String, String> componentData) {
        componentData.put("componentId", String.valueOf(id));
        componentService.updateComponent(componentData);
        return "redirect:/components";
    }

    @GetMapping("/delete/{id}")
    public String deleteComponent(@PathVariable("id") int id) {
        componentService.deleteComponentById(id);
        return "redirect:/components";
    }

    @PostMapping("/execute-query")
    public String executeQuery(@RequestParam("sqlQuery") String sqlQuery, Model model) {
        try {
            List<Map<String, Object>> result = jdbcTemplate.query(sqlQuery, new ColumnMapRowMapper());
            if (!result.isEmpty()) {
                model.addAttribute("queryResult", Map.of(
                        "columns", result.get(0).keySet(),
                        "rows", result.stream().map(Map::values).toList()
                ));
            } else {
                model.addAttribute("queryResult", null);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error executing query: " + e.getMessage());
        }
        return "components";
    }
}
