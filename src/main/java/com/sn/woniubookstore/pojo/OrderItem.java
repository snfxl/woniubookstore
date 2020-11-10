package com.sn.woniubookstore.pojo;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/28 15:31
 * @Description: 订单项
 */
public class OrderItem {

    private Integer id;
    private Integer orderId;
    private Integer goodsId;
    private Integer numbers;
    private Double totalPrice;

    /**
     * 订单项图片路径
     */
    private String image;

    /**
     * 订单项名字
     */
    private String goodName;

    /**
     * 订单项单价
     */
    private Double price;

    public OrderItem() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", numbers=" + numbers +
                ", totalPrice=" + totalPrice +
                '}';
    }
}