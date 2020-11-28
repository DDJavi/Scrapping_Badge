package com.borjamoll.scrapping.controller;


import com.borjamoll.scrapping.data.Search;
import com.borjamoll.scrapping.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService _productService;

    @GetMapping("/")
    String testl() {
        return "hola";
    }

    @PostMapping("/")
    String searchProduct(@RequestBody Search key) throws JsonProcessingException {
        return _productService.run(key);
    }


}
