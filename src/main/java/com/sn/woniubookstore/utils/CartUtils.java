package com.sn.woniubookstore.utils;

import com.sn.woniubookstore.pojo.Cart;
import com.sn.woniubookstore.pojo.CartGood;
import com.sn.woniubookstore.pojo.OrderItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 15:19
 * @Description: TODO
 */
public class CartUtils {

    public static Cart getCart(HttpServletRequest request) {

        return (Cart) request.getSession().getAttribute("cart");
    }

    public static CartGood getCartGoodById(List<CartGood> cartGoods, Integer goodsId) {

        for (CartGood cartGood : cartGoods) {

            if (cartGood.getGoodsId().equals(goodsId)) {

                return cartGood;
            }
        }

        return null;
    }

    /**
     * 判断该商品项 是不是订单项
     *
     * @param orderItems 所有的订单项
     * @param cartGood   商品项
     * @return 返回boolean
     */
    public static boolean isOrderItem(List<OrderItem> orderItems, CartGood cartGood) {

        if (orderItems.isEmpty() || cartGood == null) {

            return false;
        }

        for (OrderItem orderItem : orderItems) {

            if (orderItem.getGoodsId().equals(cartGood.getGoodsId())) {

                return true;
            }
        }

        return false;
    }

}