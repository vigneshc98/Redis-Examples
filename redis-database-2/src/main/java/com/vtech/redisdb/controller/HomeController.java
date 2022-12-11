package com.vtech.redisdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtech.redisdb.entity.Product;
import com.vtech.redisdb.repository.ProductDao;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private ProductDao productDao;

    @PostMapping("/product")
    public Product save(@RequestBody Product product){
        return productDao.save(product);
    }

    @GetMapping("/product")
    public List<Product> getAllProduct(){
        return productDao.findAll();
    }

    @GetMapping("/product/{id}")
    public Product findProduct(@PathVariable int id){
        return productDao.findProductById(id);
    }

    @DeleteMapping("/product/{id}")
    public String remove(@PathVariable int id){
        return productDao.deleteProduct(id);
    }
}
