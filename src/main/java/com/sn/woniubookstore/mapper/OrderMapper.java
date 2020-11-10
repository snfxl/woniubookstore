package com.sn.woniubookstore.mapper;

import com.sn.woniubookstore.pojo.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/28 9:40
 * @Description: TODO
 */
@Repository
public interface OrderMapper {

    /**
     * 创建订单
     * @param order 订单
     */
    void createOrder(Order order);

    /**
     * 通过订单号查询订单
     * @param orderNumber 订单号
     * @return 返回订单
     */
    Order queryOrderByOrderNumber(String orderNumber);

    /**
     * 修改订单
     * @param order 要修改的订单
     */
    void updateOrder(Order order);

    /**
     * 根据用户id查询订单
     * @param userId 用户id
     * @return 返回该用户的所有订单
     */
    List<Order> queryOrderByUserId(Integer userId);

    /**
     * 根据订单号删除订单
     * @param orderNumber 订单号
     */
    void deleteOrderByOrderNumber(String orderNumber);
}