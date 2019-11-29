package com.dao;

import com.domain.Member;
import com.domain.Orders;
import com.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrdersDao {
    //查找所有订单信息
    @Select("select * from orders")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product" ,javaType = Product.class ,one = @One(select =
            "com.dao.ProductDao.findById"))
    })
    List<Orders> findAll() throws Exception;


    //根据id查询订单详情
    @Select("select * from orders where id=#{id}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product" ,javaType = Product.class ,one = @One(select =
                    "com.dao.ProductDao.findById")),
            @Result(column = "memberId",property = "member" ,javaType = Member.class ,one = @One(select =
                    "com.dao.MemberDao.findById")),
            @Result(column = "id",property = "travellers" ,javaType = java.util.List.class ,many = @Many(select =
                    "com.dao.TravellerDao.findByOrderId"))
    })
    Orders findById(String id);
}
