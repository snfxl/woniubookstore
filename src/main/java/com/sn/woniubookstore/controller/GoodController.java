package com.sn.woniubookstore.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sn.woniubookstore.pojo.Cart;
import com.sn.woniubookstore.pojo.Good;
import com.sn.woniubookstore.pojo.User;
import com.sn.woniubookstore.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/24 15:36
 * @Description: 对商品进行添加或展示等
 */
@SessionAttributes({"user","cart"})
@Controller
public class GoodController {

    @Autowired
    private GoodService goodService;

    @GetMapping("/addGoodPage/{hotGoodId}/{goodId}")
    public String addGoodPage(Model model,
                              @PathVariable("goodId") String goodId,
                              @PathVariable("hotGoodId") String hotGoodId) {

        //1.获取要添加的商品
        Good good = goodService.queryBookById(Integer.parseInt(goodId));
        Good hotGood = goodService.queryBookById(Integer.parseInt(hotGoodId));
        //2.将商品放到域对象中
        model.addAttribute("good", good);
        model.addAttribute("hotGood", hotGood);

        return "good/good_detail";
    }


    /**
     * 根据关键字搜索商品
     *
     * @param model   向域对象中放数据
     * @param pageNum 当前页
     * @param keyword 关键字
     * @return 获取到查到的商品 并返回页面进行展示
     */
    @GetMapping("/searchGoodByKeywordPage")
    public String searchGoodByKeywordPage(Model model,
                                          @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                          @RequestParam("keyword") String keyword) {

        //1 开始分页 pageNum当前为第几页 每页6条数据
        PageHelper.startPage(pageNum, 6);

        //2 查询得到当前页的数据
        List<Good> pageNumberGoods = goodService.queryBooksByKeyword(keyword);

        if (pageNumberGoods.isEmpty()) {

            model.addAttribute("searchErrorMessage", "商品不存在！！！");
            return "good/keyword_goods";
        }

        //3 获取一个分页对象
        PageInfo<Good> page = new PageInfo<>(pageNumberGoods);

        //4 将数据放到request域中 供前端页面展示
        //当前页数据
        model.addAttribute("goods", pageNumberGoods);
        //当前页
        model.addAttribute("pageNum", page.getPageNum());
        //总记录数
        model.addAttribute("total", page.getTotal());
        //每页记录数
        model.addAttribute("size", page.getSize());
        //总页数
        model.addAttribute("pages", page.getPages());
        //关键字
        model.addAttribute("keyword", keyword);

        return "good/keyword_goods";
    }

    /**
     * @param model   将数据放到域对象中
     * @param pageNum 分类商品的当前页
     * @param type    商品的类型
     * @return 返回到分类页面
     */
    @GetMapping("/classifyPage")
    public String classifyPage(Model model,
                               @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                               @RequestParam("type") Integer type) {

        //1 开始分页 pageNum当前为第几页 每页6条数据
        PageHelper.startPage(pageNum, 6);

        //2 查询得到当前页的数据
        List<Good> pageNumberGoods = goodService.queryBooksByClass(type);

        //3 获取一个分页对象
        PageInfo<Good> page = new PageInfo<>(pageNumberGoods);

        //4 将数据放到request域中 供前端页面展示
        //当前页数据
        model.addAttribute("goods", pageNumberGoods);
        //当前页
        model.addAttribute("pageNum", page.getPageNum());
        //总记录数
        model.addAttribute("total", page.getTotal());
        //每页记录数
        model.addAttribute("size", page.getSize());
        //总页数
        model.addAttribute("pages", page.getPages());
        //商品类型
        model.addAttribute("type", type);
        //判断是哪种类型
        switch (type) {
            //it技术
            case 1:
                model.addAttribute("IT", "IT");
                model.addAttribute("goodType", "IT技术");
                break;
            //小说文学
            case 2:
                model.addAttribute("story", "story");
                model.addAttribute("goodType", "小说文学");
                break;
            //历史传记
            case 3:
                model.addAttribute("history", "history");
                model.addAttribute("goodType", "历史传记");
                break;
            //生活
            case 4:
                model.addAttribute("life", "life");
                model.addAttribute("goodType", "生活");
                break;
            //经营管理
            case 5:
                model.addAttribute("manage", "manage");
                model.addAttribute("goodType", "经营管理");
                break;
            default:
                break;
        }

        return "good/classify_goods";
    }

    /**
     * @param model       向域对象中放数据
     * @param pageNum     当前页
     * @param hotPageNum  热卖商品当前页
     * @param loginStatus 登录状态
     * @return 返回首页面
     */
    @GetMapping("/getAllGoods")
    public String getAllGoods(Model model,
                              HttpServletRequest request,
                              @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                              @RequestParam(defaultValue = "1", value = "hotPageNum") Integer hotPageNum,
                              @RequestParam(defaultValue = "1", value = "loginStatus") Integer loginStatus) {

        //1.所有商品
        //1.1 开始分页 pageNum当前为第几页 每页3条数据
        PageHelper.startPage(pageNum, 3);

        //1.2 查询得到当前页的数据
        List<Good> pageNumberGoods = goodService.queryAllGoods();

        //1.3 获取一个分页对象
        PageInfo<Good> page = new PageInfo<>(pageNumberGoods);

        //1.4 将数据放到request域中 供前端页面展示
        //当前页数据
        model.addAttribute("goods", pageNumberGoods);
        //当前页
        model.addAttribute("pageNum", page.getPageNum());
        //总记录数
        model.addAttribute("total", page.getTotal());
        //每页记录数
        model.addAttribute("size", page.getSize());
        //总页数
        model.addAttribute("pages", page.getPages());

        //2.hotPageNum
        //2.1 开始分页 pageNum当前为第几页 每页2条数据
        PageHelper.startPage(hotPageNum, 2);

        //2.2 查询得到当前页的数据
        List<Good> pageNumberHotGoods = goodService.queryHotBooks();

        //2.3 获取一个分页对象
        PageInfo<Good> hotPage = new PageInfo<>(pageNumberHotGoods);

        //2.4 将数据放到request域中 供前端页面展示
        //当前页数据
        model.addAttribute("hotGoods", pageNumberHotGoods);
        //当前页
        model.addAttribute("hotPageNum", hotPage.getPageNum());
        //总记录数
        model.addAttribute("hotTotal", hotPage.getTotal());
        //每页记录数
        model.addAttribute("hotSize", hotPage.getSize());
        //总页数
        model.addAttribute("hotPages", hotPage.getPages());

        //3.此时表示未登录
        if (loginStatus == 0) {

            model.addAttribute("user", new User());
            Cart cart = new Cart();
            cart.setTotalCount(0);
            model.addAttribute("cart", cart);
        }

        return "good/index";
    }
}