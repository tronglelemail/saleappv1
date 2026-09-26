package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public ProductService() {
        loadProducts();
        loadCategories();
    }

    private void loadProducts() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("data/products.json").getInputStream();
            products = mapper.readValue(is, new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCategories() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("data/categories.json").getInputStream();
            categories = mapper.readValue(is, new TypeReference<List<Category>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Category> getAllCategories() {
        return categories;
    }

    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public String getCategoryNameById(int categoryId) {
        for (Category c : categories) {
            if (c.getId() == categoryId) {
                return c.getName();
            }
        }
        return "Không rõ";
    }

    public List<Product> searchProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            boolean match = true;

            if (categoryId != null && p.getCategoryId() != categoryId) {
                match = false;
            }

            if (keyword != null && !keyword.isEmpty()
                    && !p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                match = false;
            }

            if (fromPrice != null && p.getPrice() < fromPrice) {
                match = false;
            }

            if (toPrice != null && p.getPrice() > toPrice) {
                match = false;
            }

            if (match) {
                result.add(p);
            }
        }
        return result;
    }
}
