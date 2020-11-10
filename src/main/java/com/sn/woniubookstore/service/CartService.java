package com.sn.woniubookstore.service;

import com.sn.woniubookstore.mapper.CartMapper;
import com.sn.woniubookstore.pojo.Cart;
import com.sn.woniubookstore.pojo.CartGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/26 11:43
 * @Description: TODO
 */
@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public Cart getCartByUserId(Integer userId){

        return cartMapper.getCartByUserId(userId);
    }

    public void saveCart(Cart cart) {

        cartMapper.saveCart(cart);
    }

    public void updateCart(Cart cart) {

        cartMapper.updateCart(cart);
    }

}