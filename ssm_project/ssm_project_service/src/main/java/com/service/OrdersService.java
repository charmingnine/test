package com.service;

import com.domain.Orders;

import java.util.List;

public interface OrdersService {
    //查询所有订单信息--未分页
    List<Orders> findAll() throws Exception;
    //查询所有订单信息--分页
    List<Orders> findAll(Integer page, Integer size) throws Exception;

    Orders findById(String id);
}
