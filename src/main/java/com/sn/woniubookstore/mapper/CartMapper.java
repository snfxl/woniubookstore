package com.sn.woniubookstore.mapper;

import com.sn.woniubookstore.pojo.Cart;
import com.sn.woniubookstore.pojo.CartGood;
import org.springframework.stereotype.Repository;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/26 11:43
 * @Description: 操作数据库mall_cart表
 */
@Repository
public interface CartMapper {

    /**
     * 根据用户id获取他的购物车
     * @param userId 用户id
     * @return 获取购物车
     */
    Cart getCartByUserId(Integer userId);

    /**
     * 向数据库中保存用户的购物车
     * @param cart 购物车
     */
    void saveCart(Cart cart);

    /**
     * 修改购物车
     * @param cart 购物车
     */
    void updateCart(Cart cart);

}
