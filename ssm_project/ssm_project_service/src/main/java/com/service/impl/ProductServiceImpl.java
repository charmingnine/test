package com.service.impl;

import com.dao.ProductDao;
import com.domain.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductDao productDao;

    //查询所有商品
    @Override
    public List<Product> findAll() throws Exception {
        return productDao.findAll();
    }

    //添加产品
    @Override
    public void save(Product product) {
        productDao.save(product);
    }
}
