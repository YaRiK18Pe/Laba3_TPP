package com.tpp.tpplab3.controllers;

import com.tpp.tpplab3.models.ProductComponent;
import com.tpp.tpplab3.service.ProductComponentService;
import com.tpp.tpplab3.service.ProductService;
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
@RequestMapping("/productcomponents")
public class ProductComponentController {

    @Autowired
    private ProductComponentService productComponentService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ComponentService componentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String listProductComponents(Model model) {
        model.addAttribute("productComponents", productComponentService.getAllProductComponents());
        return "productcomponents";
    }

    @GetMapping("/add")
    public String addProductComponentForm(Model model) {
        model.addAttribute("productComponent", new ProductComponent());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("components", componentService.getAllComponents());
        return "add-product-component";
    }

    @PostMapping("/add")
    public String addProductComponent(@ModelAttribute ProductComponent productComponent) {
        productComponentService.saveProductComponent(productComponent);
        return "redirect:/productcomponents";
    }

    @GetMapping("/edit/{id}")
    public String editProductComponentForm(@PathVariable("id") Integer id, Model model) {
        ProductComponent productComponent = productComponentService.findById(id).orElse(null);
        if (productComponent != null) {
            model.addAttribute("productComponent", productComponent);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("components", componentService.getAllComponents());
            return "edit-product-component";
        } else {
            return "redirect:/productcomponents";
        }
    }

    @PostMapping("/update/{id}")
    public String updateProductComponent(@PathVariable("id") Integer id, @ModelAttribute ProductComponent productComponent) {
        productComponent.setProductComponentId(id);
        productComponentService.updateProductComponent(productComponent);
        return "redirect:/productcomponents";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductComponent(@PathVariable("id") Integer id) {
        productComponentService.deleteById(id);
        return "redirect:/productcomponents";
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

        return "productcomponents";
    }
}
