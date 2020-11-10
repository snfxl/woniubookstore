package com.sn.woniubookstore.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 18:58
 * @Description: TODO
 */
public class Order {

    private Integer id;

    /**
     * 订单编号
     */
    private String orderNumber;

    private Integer userId;

    /**
     * 创建订单的时间
     */
    private LocalDateTime orderTime;

    /**
     * 收件人姓名
     */
    private String acceptName;

    /**
     * 收件人电话
     */
    private String phone;

    /**
     * 收件人地址
     */
    private String address;

    /**
     * 需要支付多少钱
     */
    private Double money;

    /**
     * 支付类型
     * 1 支付宝
     * 2 货到付款
     */
    private Integer payType;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态
     * 1 未付款
     * 2 已付款
     */
    private Integer status;

    /**
     * 订单项
     */
    private List<OrderItem> orderItems;

    public Order() {
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", userId=" + userId +
                ", orderTime=" + orderTime +
                ", acceptName='" + acceptName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", money=" + money +
                ", payType=" + payType +
                ", payTime=" + payTime +
                ", status=" + status +
                '}';
    }
}