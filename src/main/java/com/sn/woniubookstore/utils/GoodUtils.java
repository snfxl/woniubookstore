package com.sn.woniubookstore.utils;

import com.sn.woniubookstore.pojo.CartGood;
import com.sn.woniubookstore.pojo.Good;
import com.sn.woniubookstore.service.GoodService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 16:04
 * @Description: TODO
 */
public class GoodUtils {

    /**
     * 将商品项转换为商品
     * @param cartGoods 商品项
     * @param goodService 调用mapper中的方法
     * @return 返回商品
     */
    public static List<Good> changeToGoods(List<CartGood> cartGoods, GoodService goodService) {

        List<Good> goods = new ArrayList<>();

        for (CartGood cartGood : cartGoods) {

            Integer goodsId = cartGood.getGoodsId();
            Good good = goodService.queryBookById(goodsId);
            goods.add(good);
        }

        return goods;
    }

    /**
     * 将传过来的是json字符串 去除[] 引号 逗号
     * @param goodsIdStr 传过来的json字符串
     * @return 返回只包含id的数组
     */
    public static String[] getGoodIds(String goodsIdStr){

        //传过来的是json字符串 格式是["1","2","3"]
        //先变成 1","2","3
        String substring = goodsIdStr.substring(2, goodsIdStr.length() - 2);
        //然后再根据 "," 进行分割 得到 1 2 3

        return substring.split("\",\"");
    }

    /**
     * 判断id数组中是否包含传入的id
     * @param idArray id数组
     * @param goodId 传入的id
     * @return 是否包含
     */
    public static boolean idArrayContainGoodId(String[] idArray,Integer goodId){

        for (int i = 0; i < idArray.length; i++) {

            if (goodId.equals(Integer.parseInt(idArray[i]))){

                return true;
            }
        }

        return false;
    }


}