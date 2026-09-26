package com.example.saleappv1.controller;

import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "fromPrice", required = false) Double fromPrice,
            @RequestParam(value = "toPrice", required = false) Double toPrice,
            Model model) {

        List<Product> products;
        if (categoryId != null || keyword != null || fromPrice != null || toPrice != null) {
            products = productService.searchProducts(categoryId, keyword, fromPrice, toPrice);
        } else {
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("fromPrice", fromPrice);
        model.addAttribute("toPrice", toPrice);
        return "products";
    }

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable("productId") int productId, Model model) {
        Product product = productService.getProductById(productId);
        String categoryName = productService.getCategoryNameById(product.getCategoryId());
        model.addAttribute("product", product);
        model.addAttribute("categoryName", categoryName);
        return "product-detail";
    }
}
