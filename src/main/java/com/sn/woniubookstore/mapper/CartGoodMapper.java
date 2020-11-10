package com.sn.woniubookstore.mapper;

import com.sn.woniubookstore.pojo.CartGood;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/25 14:53
 * @Description: 操作数据库mall_cart_good表
 */
@Repository
public interface CartGoodMapper {

    /**
     * 根据购物车id 查询商品项
     * @param cartId 购物车id
     * @return 返回购物车中的所有商品项
     */
    List<CartGood> queryByCartId(Integer cartId);

    /**
     * 保存商品项
     * @param cartGood 商品项
     */
    void saveCartGood(CartGood cartGood);

    /**
     * 更新商品项
     * @param cartGood 商品项
     */
    void updateCartGood(CartGood cartGood);

    /**
     * 根据商品id删除商品项
     * @param goodsId 商品id
     */
    void deleteGoodById(Integer goodsId);

    /**
     * 根据商品id 查询商品项
     * @param goodsId 商品id
     * @return 返回商品项
     */
    CartGood queryByGoodsId(Integer goodsId);

    /**
     * 查询是否变为订单项
     * @param changeItem "y" 变为订单项
     * @return 返回查到的订单项
     */
    List<CartGood> queryByChangeItem(String changeItem);

    /**
     * 删除变成订单项的商品项
     * @param changeItem  "y" 变为订单项
     */
    void deleteByChangeItem(String changeItem);
}
