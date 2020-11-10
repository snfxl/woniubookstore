package com.sn.woniubookstore.mapper;

import com.sn.woniubookstore.pojo.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/28 16:28
 * @Description: TODO
 */
@Repository
public interface OrderItemMapper {

    /**
     * 创建订单项
     * @param orderItem 订单项
     */
    void createOrderItem(OrderItem orderItem);

    /**
     * 根据订单id查询订单项
     * @param orderId 订单id
     * @return 返回订单中的订单项
     */
    List<OrderItem> queryOrderItemByOrderId(Integer orderId);

    /**
     * 根据订单id删除订单项
     * @param orderId 订单id
     */
    void deleteOrderItemByOrderId(Integer orderId);
}
