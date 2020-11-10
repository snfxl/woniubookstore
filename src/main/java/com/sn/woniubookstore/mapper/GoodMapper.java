package com.sn.woniubookstore.mapper;

import com.sn.woniubookstore.pojo.Good;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/24 15:37
 * @Description: TODO
 */
@Repository
public interface GoodMapper {

    /**
     * 查询所有商品
     * @return 返回所有商品的集合
     */
    List<Good> queryAll();

    /**
     * 查询所有热卖商品
     * @return 返回所有热卖商品
     */
    List<Good> queryHotBooks();

    /**
     * 根据类型查询图书
     * @param category 查询的类型
     * @return 返回该类型的所有商品
     */
    List<Good> queryBooksByClass(Integer category);

    /**
     * 根据关键字查询图书
     * @param keyword 关键字
     * @return 返回书名含有该关键字的所有商品
     */
    List<Good> queryBooksByKeyword(String keyword);

    /**
     * 根据id查询商品
     * @param goodsId 商品id
     * @return 返回查到的商品
     */
    Good queryBookById(Integer goodsId);

    /**
     * 修改商品
     * @param good 要修改的商品
     */
    void updateGood(Good good);
}