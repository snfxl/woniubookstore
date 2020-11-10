package com.sn.woniubookstore.service;

import com.sn.woniubookstore.mapper.OrderMapper;
import com.sn.woniubookstore.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/28 9:40
 * @Description: TODO
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public void createOrder(Order order) {

        orderMapper.createOrder(order);
    }

    public Order queryOrderByOrderNumber(String orderNumber) {

        return orderMapper.queryOrderByOrderNumber(orderNumber);
    }

    public void updateOrder(Order order) {

        orderMapper.updateOrder(order);
    }

    public List<Order> queryOrderByUserId(Integer userId) {

        return orderMapper.queryOrderByUserId(userId);
    }

    public void deleteOrderByOrderNumber(String orderNumber) {

        orderMapper.deleteOrderByOrderNumber(orderNumber);
    }
}