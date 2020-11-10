package com.sn.woniubookstore.service;

import com.sn.woniubookstore.mapper.CartGoodMapper;
import com.sn.woniubookstore.pojo.CartGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/26 13:34
 * @Description: TODO
 */
@Service
public class CartGoodSercie {

    @Autowired
    private CartGoodMapper cartGoodMapper;

    public void saveCartGood(CartGood cartGood) {

        cartGoodMapper.saveCartGood(cartGood);
    }

    public void updateCartGood(CartGood cartGood) {

        cartGoodMapper.updateCartGood(cartGood);
    }

    public void deleteGoodById(Integer goodsId) {

        cartGoodMapper.deleteGoodById(goodsId);
    }

    public CartGood queryByGoodsId(Integer goodsId) {

        return cartGoodMapper.queryByGoodsId(goodsId);
    }

    public List<CartGood> queryByChangeItem(String changeItem) {

        return cartGoodMapper.queryByChangeItem(changeItem);
    }

    public void deleteByChangeItem(String changeItem) {

        cartGoodMapper.deleteByChangeItem(changeItem);
    }
}