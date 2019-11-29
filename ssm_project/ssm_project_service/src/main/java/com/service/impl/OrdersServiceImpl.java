package com.service.impl;

import com.dao.OrdersDao;
import com.domain.Orders;
import com.github.pagehelper.PageHelper;
import com.service.OrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Resource
    private OrdersDao ordersDao;

    @Override
    public List<Orders> findAll() throws Exception {
        //查询所有订单信息--未分页
        return ordersDao.findAll();
    }

    @Override
    public List<Orders> findAll(Integer page, Integer size) throws Exception {
        //pageNum是页码值，pageSize是每页的数据条数
        PageHelper.startPage(page,size);
        //查询所有订单信息--分页
        return ordersDao.findAll();
    }

    @Override
    public Orders findById(String id) {
        return ordersDao.findById(id);
    }
}
