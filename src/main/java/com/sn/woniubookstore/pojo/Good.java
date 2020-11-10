package com.sn.woniubookstore.pojo;

import com.jhlabs.image.PixelUtils;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/24 15:11
 * @Description: 商品
 */
public class Good {

    private Integer id;
    private String name;
    /**
     * 图书编号
     */
    private String goodNumber;
    private String author;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 出版时间
     */
    private String publisherTime;
    /**
     * 分类
     */
    private Integer category;
    /**
     * 描述
     */
    private String description;
    /**
     * 图片路径
     */
    private String image;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 市场价格
     */
    private Double marketPrice;
    /**
     * 售价
     */
    private Double salesPrice;
    /**
     * 用户评分
     */
    private Double score;

    private Integer remarkNumbers;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 销量
     */
    private Integer saleCounts;
    /**
     * 新品
     */
    private String newProduct;
    /**
     * 热卖商品
     */
    private String hot;
    private String status;

    public Good() {
    }

    public Good(Integer id, String name, String goodNumber, String author, String publisher, String publisherTime, Integer category, String description, String image, Integer stock, Double marketPrice, Double salesPrice, Double score, Integer remarkNumbers, Date updateTime, Integer saleCounts, String newProduct, String hot, String status) {
        this.id = id;
        this.name = name;
        this.goodNumber = goodNumber;
        this.author = author;
        this.publisher = publisher;
        this.publisherTime = publisherTime;
        this.category = category;
        this.description = description;
        this.image = image;
        this.stock = stock;
        this.marketPrice = marketPrice;
        this.salesPrice = salesPrice;
        this.score = score;
        this.remarkNumbers = remarkNumbers;
        this.updateTime = updateTime;
        this.saleCounts = saleCounts;
        this.newProduct = newProduct;
        this.hot = hot;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(String goodNumber) {
        this.goodNumber = goodNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherTime() {
        return publisherTime;
    }

    public void setPublisherTime(String publisherTime) {
        this.publisherTime = publisherTime;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getRemarkNumbers() {
        return remarkNumbers;
    }

    public void setRemarkNumbers(Integer remarkNumbers) {
        this.remarkNumbers = remarkNumbers;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSaleCounts() {
        return saleCounts;
    }

    public void setSaleCounts(Integer saleCounts) {
        this.saleCounts = saleCounts;
    }

    public String getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(String newProduct) {
        this.newProduct = newProduct;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", goodNumber='" + goodNumber + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publisherTime='" + publisherTime + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", stock=" + stock +
                ", marketPrice=" + marketPrice +
                ", salesPrice=" + salesPrice +
                ", score=" + score +
                ", remarkNumbers=" + remarkNumbers +
                ", updateTime=" + updateTime +
                ", saleCounts=" + saleCounts +
                ", newProduct='" + newProduct + '\'' +
                ", hot='" + hot + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}