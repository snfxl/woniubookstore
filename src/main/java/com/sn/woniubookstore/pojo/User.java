package com.sn.woniubookstore.pojo;

import java.time.LocalDateTime;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/21 11:52
 * @Description: TODO
 */
public class User {

    private Integer id;
    private String account;
    private String password;
    private String email;
    /**
     * 头像引用路径
     */
    private String avatar;
    /**
     * 积分
     */
    private Integer score;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 购物车
     */
    private Cart cart;

    public User() {

    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User(Integer id, String account, String password, String email, String avatar, Integer score, LocalDateTime registerTime, String status) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.score = score;
        this.registerTime = registerTime;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", score=" + score +
                ", registerTime=" + registerTime +
                ", status='" + status + '\'' +
                '}';
    }
}