package com.vtech.redisdb.controller;

import com.vtech.redisdb.entity.Product;
import com.vtech.redisdb.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@EnableCaching
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
    @Cacheable(key="#id",value="Product", unless="#result.price > 1000")   //value= hashValue  //unless=Product price less than 1000 will be cached, >1000 will be fetching from the db
    public Product findProduct(@PathVariable int id){
        return productDao.findProductById(id);
    }

    @DeleteMapping("/product/{id}")
    @CacheEvict(key="#id",value="Product")       //if we delete from db it wil reflect in cache
    public String remove(@PathVariable int id){
        return productDao.deleteProduct(id);
    }
    
}
