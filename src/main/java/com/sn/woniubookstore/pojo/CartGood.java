package com.sn.woniubookstore.pojo;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/25 14:14
 * @Description: TODO
 */
public class CartGood {

    private Integer id;
    /**
     * 购物车id
     */
    private Integer cartId;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品单价
     */
    private Double price;
    /**
     * 数量
     */
    private Integer numbers;
    /**
     * 添加时间
     */
    private LocalDateTime addTime;
    /**
     * 是否支付
     */
    private String pay;
    /**
     * 商品总价
     */
    private Double totalPrice;

    /**
     * 商品图片路径
     */
    private String image;

    /**
     * 商品名字
     */
    private String goodName;

    /**
     * 是否变成订单项
     * n 不是
     * y 是
     */
    private String changeOrderItem;

    public CartGood() {

    }

    public String getChangeOrderItem() {
        return changeOrderItem;
    }

    public void setChangeOrderItem(String changeOrderItem) {
        this.changeOrderItem = changeOrderItem;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {

        this.numbers = numbers;
        this.totalPrice = numbers * price;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "CartGood{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", goodsId=" + goodsId +
                ", price=" + price +
                ", numbers=" + numbers +
                ", addTime=" + addTime +
                ", pay='" + pay + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}