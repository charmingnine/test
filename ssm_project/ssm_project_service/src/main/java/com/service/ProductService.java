package com.service;

import com.domain.Product;

import java.util.List;

public interface ProductService {
    //查询产品
    List<Product> findAll() throws Exception;
    //添加产品
    void save(Product product);
}
