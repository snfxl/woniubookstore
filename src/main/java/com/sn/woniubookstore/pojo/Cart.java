package com.sn.woniubookstore.pojo;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/26 11:37
 * @Description: 购物车
 */
public class Cart {

    private Integer id;
    private Integer userId;
    /**
     * 购物车中总数量
     */
    private Integer totalCount;

    /**
     * 商品总价格
     */
    private Double totalPrice;

    /**
     * 购物车中商品项
     */
    private List<CartGood> cartGoods;

    public Cart() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartGood> getCartGoods() {
        return cartGoods;
    }

    public void setCartGoods(List<CartGood> cartGoods) {
        this.cartGoods = cartGoods;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalCount=" + totalCount +
                ", totalPrice=" + totalPrice +
                ", cartGoods=" + cartGoods +
                '}';
    }
}