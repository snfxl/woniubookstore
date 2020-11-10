package com.sn.woniubookstore.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sn.woniubookstore.annotation.NoRepeatSubmit;
import com.sn.woniubookstore.pojo.*;
import com.sn.woniubookstore.service.*;
import com.sn.woniubookstore.utils.CartUtils;
import com.sn.woniubookstore.utils.GoodUtils;
import com.sn.woniubookstore.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 14:18
 * @Description: TODO
 */
@SessionAttributes("cart")
@Controller
public class OrderController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private GoodService goodService;

    @Autowired
    private CartGoodSercie cartGoodSercie;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional
    @DeleteMapping("/deleteOrder")
    public String deleteOrder(String orderNumber) {

        //1.获取要删除的订单
        Order order = orderService.queryOrderByOrderNumber(orderNumber);

        //2.根据订单id删除订单项目
        orderItemService.deleteOrderItemByOrderId(order.getId());

        //3.删除订单
        orderService.deleteOrderByOrderNumber(orderNumber);

        return "redirect:/myOrderPage";
    }

    @GetMapping("/orderDetail/{orderNumber}")
    public String orderDetail(@PathVariable("orderNumber") String orderNumber,
                              Model model) {
        //1.根据订单编号查询订单
        Order order = orderService.queryOrderByOrderNumber(orderNumber);

        //2.将订单放到域对象中回显
        model.addAttribute("order", order);

        return "order/order_detail";
    }

    @GetMapping("/paySuccessPage/{orderNumber}")
    public String paySuccessPage(Model model,
                                 @PathVariable("orderNumber") String orderNumber) {

        //1.添加position 告诉浏览器当前在哪个位置
        model.addAttribute("position", "paySuccess");

        //2.根据订单编号查询订单
        Order order = orderService.queryOrderByOrderNumber(orderNumber);

        //3.将订单放到域对象中回显
        model.addAttribute("order", order);

        return "order/pay_success";
    }

    /**
     * 支付成功 修改订单支付状态
     *
     * @param model       操作域数据
     * @param orderNumber 当前订单
     * @return 返回到支付成功页面
     */
    @Transactional
    @GetMapping("/paySuccess/{orderNumber}")
    public String paySuccess(Model model, @PathVariable("orderNumber") String orderNumber) {

        //1.修改订单支付状态 支付时间
        Order order = orderService.queryOrderByOrderNumber(orderNumber);

        //判断订单状态是否为已支付 如果是已支付直接返回首页
        if (order.getStatus().equals(2)) {

            return "redirect:/getAllGoods";
        }

        order.setStatus(2);
        order.setPayTime(LocalDateTime.now());

        //2.修改商品的销量和库存
        //2.1 获取该订单的所有订单项
        List<OrderItem> orderItems = orderItemService.queryOrderItemByOrderId(order.getId());

        //2.2 遍历获取对应的商品
        for (OrderItem orderItem : orderItems) {

            Good good = goodService.queryBookById(orderItem.getGoodsId());
            good.setSaleCounts(good.getSaleCounts() + orderItem.getNumbers());
            good.setStock(good.getStock() - orderItem.getNumbers());
            //在数据库中修改
            goodService.updateGood(good);
        }

        //3.在数据库中修改订单
        orderService.updateOrder(order);

        return "redirect:/paySuccessPage/" + orderNumber;
    }

    /**
     * 前往支付页面
     *
     * @param orderNumber 订单标号
     * @param model       操作域中数据
     * @return 返回支付页面
     */
    @GetMapping("/payPage/{orderNumber}")
    public String payPage(@PathVariable("orderNumber") String orderNumber, Model model) {

        //1.添加position 告诉浏览器当前在哪个位置
        model.addAttribute("position", "pay");

        //2.获取当前订单
        Order order = orderService.queryOrderByOrderNumber(orderNumber);

        //3.判断当前订单是否已经支付 如果已经支付直接返回到首页
        if (order.getStatus().equals(2)) {

            return "redirect:/getAllGoods";
        }

        //4.在页面回显订单信息
        model.addAttribute("order", order);

        return "order/pay";
    }

    /**
     * 跳转到成功提交订单页面
     *
     * @param model       操作域中数据
     * @param orderNumber 订单标号
     * @return 返回到成功提交订单页面
     */
    @GetMapping("/successCreateOrderPage")
    public String successCreateOrderPage(Model model,
                                         @RequestParam("orderNumber") String orderNumber) {

        //1.添加position 告诉浏览器当前在哪个位置
        model.addAttribute("position", "success");

        //2.从数据库查询要回显的订单信息
        Order order = orderService.queryOrderByOrderNumber(orderNumber);

        //3.将订单信息放到域中 让页面回显
        model.addAttribute("order", order);

        return "order/success_submit_order";
    }

    /**
     * 生成订单 并将数据保存到数据库
     *
     * @param order      订单
     * @param updateCart 为1时说明是点击的立即购买
     * @return 重定向到生成订单页面
     */
    @Transactional
    @PostMapping("/createOrder/{goodsId}/{numbers}")
    public String createOrder(HttpServletRequest request, Order order, Model model,
                              @PathVariable(value = "goodsId", required = false) Integer goodsId,
                              @PathVariable(value = "numbers", required = false) Integer numbers,
                              @RequestParam(value = "updateCart", defaultValue = "0") Integer updateCart) {

        //1.创建订单
        //1.1 获取当前登录的用户
        User user = UserUtils.getLoginUser(request);

        //1.2 设置订单用户id
        order.setUserId(user.getId());

        //1.3 设置订单生成时间
        order.setOrderTime(LocalDateTime.now());

        //1.4 设置订单编号
        order.setOrderNumber(System.currentTimeMillis() + user.getId() + "");

        //1.5 设置支付状态 未付款
        order.setStatus(1);

        //1.6 将订单保存到数据库
        orderService.createOrder(order);

        //2. 判断是否是通过购物车来提交订单 如果不是就不更新购物车 说明是点击直接购买按钮来创建订单
        if (updateCart == 1) {

            //生成订单项
            //2.1 获取要生成订单项的商品
            Good good = goodService.queryBookById(goodsId);

            //3.2 创建订单项
            OrderItem orderItem = changeGoodToOrderItem(good, numbers);

            //3.3 将订单项添加到订单中
            orderItem.setOrderId(order.getId());

            //3.4 保存到数据库
            orderItemService.createOrderItem(orderItem);

            return "redirect:/successCreateOrderPage?orderNumber=" + order.getOrderNumber();
        }

        //3.如果是通过购物车来提交订单 就获取对应商品的订单项 并保存到数据库
        //3.1 获取购物车中变成订单项的对应的商品项
        List<CartGood> cartGoodItems = cartGoodSercie.queryByChangeItem("y");

        //3.2 创建对应的订单项 并将其添加到订单中 并保存到数据库中 并获取所有订单项的总数量
        OrderItem orderItem = new OrderItem();
        Integer totalCount = 0;

        for (CartGood cartGoodItem : cartGoodItems) {

            //3.3 为订单项属性赋值
            orderItem.setGoodsId(cartGoodItem.getGoodsId());
            orderItem.setNumbers(cartGoodItem.getNumbers());
            orderItem.setTotalPrice(cartGoodItem.getTotalPrice());
            orderItem.setImage(cartGoodItem.getImage());
            orderItem.setGoodName(cartGoodItem.getGoodName());
            orderItem.setPrice(cartGoodItem.getPrice());
            //将订单项加入对应订单
            orderItem.setOrderId(order.getId());
            //将订单项保存到数据库中
            orderItemService.createOrderItem(orderItem);
            //获取数量
            totalCount += cartGoodItem.getNumbers();
        }

        //4. 修改购物车
        //4.1 删除已经变为订单项的商品项
        cartGoodSercie.deleteByChangeItem("y");

        //4.2 获取购物车
        Cart cart = cartService.getCartByUserId(user.getId());

        //4.3 修改购物车中商品的总价格和总数量
        cart.setTotalPrice(cart.getTotalPrice() - order.getMoney());
        cart.setTotalCount(cart.getTotalCount() - totalCount);

        //4.3 更新购物车
        model.addAttribute("cart", cart);
        cartService.updateCart(cart);

        return "redirect:/successCreateOrderPage?orderNumber=" + order.getOrderNumber();
    }

    /**
     * 从购物车进行结算
     * @param model 向域对象中存取数据
     * @return 跳转到确认订单页面
     */
    @GetMapping("/checkOrderPage")
    public String checkOrderPage(HttpServletRequest request, Model model,
                                 @RequestParam("goodsIdStr") String goodsIdStr) {

        //1.添加position 告诉浏览器当前在哪个位置
        model.addAttribute("position", "order");

        //2. 设置收货地址
        //1.1 获取当前登录的用户
        User user = UserUtils.getLoginUser(request);

        //2.2 获取该用户的收货地址
        List<Address> addresses = addressService.getAllAddressByUserId(user.getId());

        //2.3 判断该用户是否添加过地址  如果没有提示去添加地址
        if (addresses.isEmpty()) {

            return "redirect:/addAddressPage/1/0";
        }

        //2.4 将地址放到域对象中 让页面来进行显示
        model.addAttribute("addresses", addresses);

        //3. 获取用户需要结算的商品的id 即哪些商品需要设置为订单项
        String[] goodsId = GoodUtils.getGoodIds(goodsIdStr);

        //4. 获取购物车 并获取购物车中存放的商品项
        Cart cart = cartService.getCartByUserId(user.getId());
        //商品项
        List<CartGood> cartGoods = cart.getCartGoods();
        //保存变成商品项的订单项 用来在页面回显
        List<CartGood> CartGoodItems = new ArrayList<>();

        //5.将商品项改变为订单项
        for (int i = 0; i < goodsId.length; i++) {

            //5.1 获取要变成订单项的商品项
            CartGood cartGoodById = CartUtils.getCartGoodById(cartGoods, Integer.parseInt(goodsId[i]));

            if (cartGoodById == null) {

                return "redirect:/getAllGoods";
            }

            //5.2 将商品项改为订单项
            cartGoodById.setChangeOrderItem("y");
            //5.3 更新到数据库
            cartGoodSercie.updateCartGood(cartGoodById);
            //5.4 添加到集合中
            CartGoodItems.add(cartGoodById);
        }

        //5.将订单项添加到域中用来回显
        model.addAttribute("CartGoodItems", CartGoodItems);

        return "order/ensure_order_from_cart";
    }

    /**
     * 立即购买
     * @param model 向域对象中存取数据
     * @return 跳转到确认订单页面
     */
    @GetMapping("/checkOrderPageByAtOnce/{goodsId}/{numbers}")
    public String checkOrderPageByAtOnce(HttpServletRequest request, Model model,
                                         @PathVariable("goodsId") Integer goodsId,
                                         @PathVariable("numbers") Integer numbers) {

        //1.添加position 告诉浏览器当前在哪个位置
        model.addAttribute("position", "order");

        //2. 设置收货地址
        //1.1 获取当前登录的用户
        User user = UserUtils.getLoginUser(request);

        //2.2 获取该用户的收货地址
        List<Address> addresses = addressService.getAllAddressByUserId(user.getId());

        //2.3 判断该用户是否添加过地址  如果没有提示去添加地址
        if (addresses.isEmpty()) {

            return "redirect:/addAddressPage/1/0";
        }

        //2.4 将地址放到域对象中 让页面来进行显示
        model.addAttribute("addresses", addresses);

        //3.生成订单项
        //3.1 获取要生成订单项的商品
        Good good = goodService.queryBookById(goodsId);

        //3.2 创建订单项
        OrderItem orderItem = changeGoodToOrderItem(good, numbers);

        //4.将订单项添加到域中用来回显
        model.addAttribute("orderItemAtOnce", orderItem);
        model.addAttribute("goodsId", goodsId);

        return "order/ensure_order_from_immediately";
    }

    /**
     * @return 跳转到我的订单页面
     */
    @GetMapping("/myOrderPage")
    public String myOrderPage(Model model, HttpServletRequest request,
                              @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {

        //1.告诉浏览器在哪个页面
        model.addAttribute("myOrderPage", "myOrderPage");

        //2. 开始分页 pageNum当前为第几页 每页6条数据
        PageHelper.startPage(pageNum, 6);

        //3.获取用户的当前页数据
        List<Order> ordersPageNum = orderService.queryOrderByUserId(UserUtils.getLoginUser(request).getId());

        //4.获取一个分页对象
        PageInfo<Order> page = new PageInfo<>(ordersPageNum);

        //5.将数据放到request域中 供前端页面展示
        //当前页数据
        model.addAttribute("orders", ordersPageNum);
        //当前页
        model.addAttribute("pageNum", page.getPageNum());
        //总记录数
        model.addAttribute("total", page.getTotal());
        //每页记录数
        model.addAttribute("size", page.getSize());
        //总页数
        model.addAttribute("pages", page.getPages());

        return "order/orders";
    }

    /**
     * 将商品变成订单项
     *
     * @param good    商品
     * @param numbers 商品数量
     * @return 返回订单项
     */
    private OrderItem changeGoodToOrderItem(Good good, Integer numbers) {

        //1. 创建订单项 并将其添加到订单中
        OrderItem orderItem = new OrderItem();

        //2. 为订单项属性赋值
        orderItem.setGoodsId(good.getId());
        orderItem.setNumbers(numbers);
        orderItem.setTotalPrice(good.getSalesPrice() * numbers);
        orderItem.setImage(good.getImage());
        orderItem.setGoodName(good.getName());
        orderItem.setPrice(good.getSalesPrice());

        return orderItem;
    }
}