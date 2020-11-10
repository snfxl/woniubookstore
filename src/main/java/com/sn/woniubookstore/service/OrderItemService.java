package com.sn.woniubookstore.service;

import com.sn.woniubookstore.mapper.OrderItemMapper;
import com.sn.woniubookstore.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/28 16:27
 * @Description: TODO
 */
@Service
public class OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    public void createOrderItem(OrderItem orderItem) {

        orderItemMapper.createOrderItem(orderItem);
    }

    public List<OrderItem> queryOrderItemByOrderId(Integer orderId) {

        return orderItemMapper.queryOrderItemByOrderId(orderId);
    }

    public void deleteOrderItemByOrderId(Integer orderId) {

        orderItemMapper.deleteOrderItemByOrderId(orderId);
    }
}