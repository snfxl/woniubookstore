package com.sn.woniubookstore.controller;

import com.sn.woniubookstore.annotation.NoRepeatSubmit;
import com.sn.woniubookstore.pojo.Cart;
import com.sn.woniubookstore.pojo.CartGood;
import com.sn.woniubookstore.pojo.Good;
import com.sn.woniubookstore.pojo.User;
import com.sn.woniubookstore.service.CartGoodSercie;
import com.sn.woniubookstore.service.CartService;
import com.sn.woniubookstore.service.GoodService;
import com.sn.woniubookstore.utils.CartUtils;
import com.sn.woniubookstore.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/26 11:43
 * @Description: TODO
 */
@SessionAttributes("cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartGoodSercie cartGoodSercie;

    @Autowired
    private GoodService goodService;

    @ResponseBody
    @PostMapping("/addNumber")
    public CartGood addNumber(Integer numbers,Integer goodId,HttpServletRequest request){

        //1.修改商品项
        CartGood cartGood = cartGoodSercie.queryByGoodsId(goodId);
        cartGood.setNumbers(numbers);

        //2.修改购物车
        Cart cart = CartUtils.getCart(request);
        cart.setTotalCount(cart.getTotalCount() + 1);
        cart.setTotalPrice(cart.getTotalPrice() + cartGood.getPrice());

        //3.更新购物车商品项
        cartService.updateCart(cart);
        cartGoodSercie.updateCartGood(cartGood);

        return cartGood;
    }

    @ResponseBody
    @PostMapping("/reduceNumber")
    public CartGood reduceNumber(Integer numbers,Integer goodId,HttpServletRequest request){

        //1.修改商品项
        CartGood cartGood = cartGoodSercie.queryByGoodsId(goodId);
        cartGood.setNumbers(numbers);

        //2.修改购物车
        Cart cart = CartUtils.getCart(request);
        cart.setTotalCount(cart.getTotalCount() - 1);
        cart.setTotalPrice(cart.getTotalPrice() - cartGood.getPrice());

        //3.更新购物车商品项
        cartService.updateCart(cart);
        cartGoodSercie.updateCartGood(cartGood);

        return cartGood;
    }

    /**
     * 删除购物车中商品
     *
     * @param model   操作域对象中的数据
     * @param request 请求相关内容
     * @param goodsId 要删除的商品id
     * @return 回到购物车页面
     */
    @Transactional
    @GetMapping("/deleteGoodFromCart/{goodsId}")
    public String deleteGoodFromCart(Model model, HttpServletRequest request,
                                     @PathVariable("goodsId") Integer goodsId) {

        //1.从数据库中获取购物车
        Cart cart = cartService.getCartByUserId(UserUtils.getLoginUser(request).getId());

        //2.删除购物车中的商品项
        List<CartGood> cartGoods = cart.getCartGoods();

        for (CartGood cartGood : cartGoods) {

            //找到进行删除
            if (cartGood.getGoodsId().equals(goodsId)) {

                //2.1 删除
                cartGoods.remove(cartGood);

                //2.2 从数据库商品项表中删除该商品项
                cartGoodSercie.deleteGoodById(goodsId);

                //2.3 修改购物车中总数量 总价格
                cart.setTotalCount(cart.getTotalCount() - cartGood.getNumbers());
                cart.setTotalPrice(cart.getTotalPrice() - cartGood.getTotalPrice());
                break;
            }
        }

        //3.设置购物车中的商品项
        cart.setCartGoods(cartGoods);

        //4.更新session中的购物车
        model.addAttribute("cart", cart);

        //5.更新数据库中的购物车
        cartService.updateCart(cart);

        return "redirect:/cartPage";
    }

    /**
     * @return 跳转到购物车页面
     */
    @GetMapping("/cartPage")
    public String cartPage(Model model, HttpServletRequest request) {

        //1.告诉浏览器当前位置
        model.addAttribute("position", "cart");

        //2.获取当前用户的购物车 从数据库中
        Cart cart = cartService.getCartByUserId(UserUtils.getLoginUser(request).getId());

        //3.获取购物车中的所有商品项
        List<CartGood> cartGoods = cart.getCartGoods();

        //4.如果购物车中没有商品项就给用户显示一句话
        if (cartGoods.isEmpty()) {

            model.addAttribute("cartMessage", "购物车中还没有商品,快去与小伙伴一起购物吧！！！");
            return "cart/cart";
        }

        //5.更新session中的cart
        model.addAttribute("cart", cart);

        return "cart/cart";
    }

    /**
     * 向购物车中添加商品
     *
     * @param model       向域对象中存取数据
     * @param request     获取请求内容的servlet
     * @param goodId      要添加的商品id
     * @param goodNumbers 要添加的商品数量
     * @return 重定向到首页
     * @Transactional 同时操作数据库多张表 如果出错就对数据进行回滚
     */
    @Transactional
    @GetMapping("/addCart/{goodId}/{goodNumbers}")
    public String addCart(HttpServletRequest request,
                          Model model,
                          @PathVariable("goodId") Integer goodId,
                          @PathVariable("goodNumbers") Integer goodNumbers) {

        //1.从session中获取当前登录用户
        User user = (User) request.getSession().getAttribute("user");

        //2. 获取当前用户的购物车
        Cart cart = cartService.getCartByUserId(user.getId());

        //3. 获取要添加到购物车的商品
        Good good = goodService.queryBookById(goodId);

        //4. 将该商品变为商品项
        CartGood cartGood = changeGoodToCartGood(good);

        //5. 将商品项添加到购物车中
        cartGood.setCartId(cart.getId());

        //6. 设置商品项加入购物车时间 和是否改变为商品项
        cartGood.setAddTime(LocalDateTime.now());
        cartGood.setChangeOrderItem("n");

        //7.设置商品的数量
        //7.1 获取购物车中所有商品
        List<CartGood> cartGoods = cart.getCartGoods();

        //7.2 判断购物车中的商品是否包含当前要添加的商品
        CartGood cartGoodHasAdd = cartHasGood(cartGoods, cartGood);
        if (cartGoodHasAdd != null) {

            //如果有 就将数量在原来的基础上 加上当前要添加的数量
            cartGood.setNumbers(cartGoodHasAdd.getNumbers() + goodNumbers);

            //在cart中保存商品项的集合中删除该商品 然后在下面再添加
            cartGoods.remove(cartGoodHasAdd);

            //在存放商品项的表中更新该商品项即可
            cartGoodSercie.updateCartGood(cartGood);

        } else {

            //如果没有 就设置其数量为当前要添加的数量
            cartGood.setNumbers(goodNumbers);

            //并将其添加到数据库存放商品项的表中
            cartGoodSercie.saveCartGood(cartGood);
        }

        //7.3 在cart中保存商品项的集合中 添加该商品项
        cartGoods.add(cartGood);

        //8.设置购物车
        //8.1 设置购物车的总数量
        cart.setTotalCount(cart.getTotalCount() + goodNumbers);

        //8.2 设置购物车中商品的总价格
        cart.setTotalPrice(cart.getTotalPrice() + cartGood.getTotalPrice());

        //8.3 设置购物车中 保存所有商品项的集合
        cart.setCartGoods(cartGoods);

        //8.4 在购物车表中修改购物车
        cartService.updateCart(cart);

        //9.更新session中的购物车
        model.addAttribute("cart", cart);

        return "redirect:/getAllGoods";
    }

    /**
     * 将商品转换为商品项
     *
     * @param good 要转换的商品
     * @return 返回商品项
     */
    private CartGood changeGoodToCartGood(Good good) {

        CartGood cartGood = new CartGood();

        cartGood.setGoodsId(good.getId());
        cartGood.setPrice(good.getSalesPrice());
        cartGood.setImage(good.getImage());
        cartGood.setGoodName(good.getName());

        return cartGood;
    }

    /**
     * @param cartGoods 购物车中的所有商品
     * @param cartGood  当前添加的商品
     * @return 判断当前添加的商品是否在购物车中
     */
    private CartGood cartHasGood(List<CartGood> cartGoods, CartGood cartGood) {

        if (cartGoods.isEmpty()) {

            return null;
        }

        for (CartGood cartGoodFromData : cartGoods) {

            if (cartGoodFromData.getGoodsId().equals(cartGood.getGoodsId())) {

                return cartGoodFromData;
            }
        }

        return null;
    }
}
