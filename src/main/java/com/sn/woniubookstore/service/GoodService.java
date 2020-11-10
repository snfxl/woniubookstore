package com.sn.woniubookstore.service;

import com.sn.woniubookstore.mapper.GoodMapper;
import com.sn.woniubookstore.pojo.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/24 15:37
 * @Description: TODO
 */
@Service
public class GoodService {

    @Autowired
    private GoodMapper goodMapper;

    public List<Good> queryAllGoods(){

        return goodMapper.queryAll();
    }

    public List<Good> queryHotBooks(){

        return goodMapper.queryHotBooks();
    }

    public List<Good> queryBooksByClass(Integer category){

        return goodMapper.queryBooksByClass(category);
    }

    public List<Good> queryBooksByKeyword(String keyword) {

        return goodMapper.queryBooksByKeyword(keyword);
    }

    public Good queryBookById(Integer goodsId) {

        return goodMapper.queryBookById(goodsId);
    }

    public void updateGood(Good good) {

        goodMapper.updateGood(good);
    }
}