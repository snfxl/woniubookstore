package com.sn.woniubookstore.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.code.kaptcha.Constants;
import com.sn.woniubookstore.pojo.Cart;
import com.sn.woniubookstore.pojo.Order;
import com.sn.woniubookstore.pojo.User;
import com.sn.woniubookstore.service.CartService;
import com.sn.woniubookstore.service.OrderService;
import com.sn.woniubookstore.service.UserService;
import com.sn.woniubookstore.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/21 11:57
 * @Description: 控制用户的登录和注册
 */
@SessionAttributes({"user", "cart"})
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/exist")
    public String exist(HttpServletRequest request) {

        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("cart");
        return "redirect:/getAllGoods?loginStatus=0";
    }

    /**
     * @return 跳转到登录页面
     */
    @GetMapping("/loginPage")
    public String loginPage() {

        return "user/login";
    }

    /**
     * @return 跳转到注册页面
     */
    @GetMapping("/registerPage")
    public String registerPage() {

        return "user/register";
    }

    /**
     * 处理注册请求
     *
     * @param user  注册的用户信息
     * @param model 向request域中放数据
     * @return 注册成功返回登陆页面
     */
    @Transactional
    @PostMapping("/register")
    public String register(User user, Model model,
                           @RequestParam("repeatedPassword") String repeatedPassword,
                           @RequestParam("code") String code, HttpServletRequest request) {

        //1.获取正确的验证码(放在session域中) 并将其从session域中删除
        String codeCorrect = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);


        //2.判断验证码是否正确
        if ("".equals(code) || !codeCorrect.equals(code)) {

            model.addAttribute("registerPageErrorMessage", "验证码错误");
            model.addAttribute("account", user.getAccount());
            model.addAttribute("password", user.getPassword());
            model.addAttribute("repeatedPassword", repeatedPassword);
            return "user/register";
        }

        //2.进行非空判断 如果为空 发送错误消息
        String account = user.getAccount();
        String password = user.getPassword();
        if ("".equals(password) || "".equals(account)) {

            model.addAttribute("registerPageErrorMessage", "账号或密码为空");
            return "user/register";
        }

        //3.判断两次密码输入是否一致 如果不一致发送错误消息
        if (!password.equals(repeatedPassword)) {

            model.addAttribute("registerPageErrorMessage", "两次密码不一致");
            model.addAttribute("account", account);
            model.addAttribute("password", password);
            return "user/register";
        }

        //4.通过用户名查询数据库中是否已经存在该用户
        User userFromData = userService.getUserByAccount(account);

        //5.如果不为null说明存在 向页面发送错误信息
        if (userFromData != null) {

            model.addAttribute("registerPageErrorMessage", "该账号已存在");
            return "user/register";
        }

        //6.将用户保存到数据库 并返回登陆页面
        userService.saveUser(user);

        //7.为该用户创建一个购物车 并保存到数据库
        Cart cart = new Cart();
        cart.setUserId(user.getId());
        cart.setTotalCount(0);
        cart.setTotalPrice(0.0);
        cartService.saveCart(cart);

        return "redirect:/loginPage";
    }


    /**
     * 处理登陆请求
     *
     * @param user  登陆获取的用户名
     * @param model 向request域中放数据
     * @return 登陆成功返回到首页
     */
    @PostMapping("/login")
    public String login(User user, Model model, @RequestParam("code") String code,
                        HttpServletRequest request) {

        //1.获取正确的验证码(放在session域中) 并将其从session域中删除
        String codeCorrect = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);

        //2.判断验证码是否正确
        if ("".equals(code) || !codeCorrect.equals(code)) {

            model.addAttribute("loginPageErrorMessage", "验证码错误");
            model.addAttribute("account", user.getAccount());
            model.addAttribute("password", user.getPassword());
            return "user/login";
        }

        //3.判断用户用或密码是否为空
        String account = user.getAccount();
        String password = user.getPassword();

        if ("".equals(account) || "".equals(password)) {

            model.addAttribute("loginPageErrorMessage", "用户名或密码为空");
            return "user/login";
        }

        //4.根据用户名从数据库获取数据
        User userFromData = userService.getUserByAccount(account);

        //5.判断用户是否存在
        if (userFromData == null) {

            model.addAttribute("loginPageErrorMessage", "用户不存在");
            return "user/login";
        }

        //6.判断密码是否正确
        if (!password.equals(userFromData.getPassword())) {

            model.addAttribute("loginPageErrorMessage", "密码错误");
            model.addAttribute("account", account);
            return "user/login";
        }

        //7.用户可以登陆  将用户放到session域中
        model.addAttribute("user", userFromData);

        //8.获取当前用户的购物车
        Cart cart = cartService.getCartByUserId(userFromData.getId());

        //9.将该用户的购物车数据放到域对象中  用来在页面中显示
        model.addAttribute("cart", cart);

        return "redirect:/getAllGoods";
    }

    /**
     * @return 跳转到个人中心页面
     */
    @GetMapping("/personalCenterPage")
    public String personalCenterPage(Model model, HttpServletRequest request,
                                     @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {

        //1.告诉浏览器在哪个页面
        model.addAttribute("personalCenterPage", "personalCenterPage");

        //2.获取总的消费金额 待付款商品数
        List<Order> orders = orderService.queryOrderByUserId(UserUtils.getLoginUser(request).getId());
        Double totalPrice = 0.0;
        Integer waitPayNumber = 0;
        for (Order order : orders) {

            if (order.getStatus().equals(2)) {

                totalPrice += order.getMoney();
            } else {

                waitPayNumber += 1;
            }
        }

        //3. 开始分页 pageNum当前为第几页 每页2条数据
        PageHelper.startPage(pageNum, 2);

        //4.获取用户的当前页数据
        List<Order> ordersPageNum = orderService.queryOrderByUserId(UserUtils.getLoginUser(request).getId());

        //5.获取一个分页对象
        PageInfo<Order> page = new PageInfo<>(ordersPageNum);

        //6.将数据放到request域中 供前端页面展示
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
        //消费总金额
        model.addAttribute("totalPrice", totalPrice);
        //待付款订单数
        model.addAttribute("waitPayNumber", waitPayNumber);

        return "user/personal_center";
    }

    /**
     * 更新密码
     *
     * @return 回到个人中心
     */
    @Transactional
    @PostMapping("/updatePassword")
    public String updatePassword(HttpServletRequest request, Model model, String oldPassword, String newPassword, String repeatPassword) {

        User user = UserUtils.getLoginUser(request);

        //1.判断旧密码与输入是否相同
        if (user.getPassword().equals(oldPassword)) {

            //2.判断是否输入新密码
            if ("".equals(newPassword)) {

                model.addAttribute("updatePasswordErrorMessage", "请输入");
                model.addAttribute("oldPassword", oldPassword);
                return "user/updatePassword";
            }

            //3.判断新密码是否与重复相同
            if (newPassword.equals(repeatPassword)) {

                //4.保存
                user.setPassword(newPassword);
                userService.updateUser(user);
            } else {

                model.addAttribute("updatePasswordErrorMessage", "两次密码不一致");
                model.addAttribute("oldPassword", oldPassword);
                return "user/updatePassword";
            }

        } else {

            model.addAttribute("updatePasswordErrorMessage", "旧密码错误");
            return "user/updatePassword";
        }

        return "redirect:/personalCenterPage";
    }

    /**
     * 跳转到更新密码页面
     *
     * @return 更新密码页面
     */
    @GetMapping("/updatePasswordPage")
    public String updatePasswordPage() {

        return "user/updatePassword";
    }

    /**
     * @return 跳转到更新头像页面
     */
    @GetMapping("/updatePhotoPage")
    public String updatePhotoPage() {

        return "user/update_photo";
    }

    /**
     * 更新头像
     *
     * @return 重定向到个人中心页面
     */
    @Transactional
    @PostMapping("/updatePhoto")
    public String updatePhoto(MultipartFile photo, HttpServletRequest request) {

        //1.获取当前用户
        User user = UserUtils.getLoginUser(request);

        //2.判断是否上传
        if (photo != null) {

            try {

                String[] strings = photo.getOriginalFilename().split("\\.");

                String name = strings[0]+ System.currentTimeMillis() + "." + strings[1];

                //3.将上传的头像保存到本地
                photo.transferTo(new File("E:\\ideaproject\\woniu-bookstore\\src\\main\\resources\\static\\images\\" + name));

                //4.保存用户头像
                user.setAvatar("images/" + name);

                //5.更新到数据库
                userService.updateUser(user);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/personalCenterPage";
    }
}
