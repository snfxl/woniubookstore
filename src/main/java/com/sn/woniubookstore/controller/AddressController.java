package com.sn.woniubookstore.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sn.woniubookstore.pojo.Address;
import com.sn.woniubookstore.pojo.User;
import com.sn.woniubookstore.service.AddressService;
import com.sn.woniubookstore.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 13:22
 * @Description: TODO
 */
@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Transactional
    @DeleteMapping("/deleteAddress")
    public String deleteAddress(Integer addressId,Integer pageNum){

        addressService.deleteAddressByAddressId(addressId);

        return "redirect:/manageAddressPage?pageNum=" + pageNum;
    }

    /**
     * 修改地址
     * @param updateAddress 更新的地址
     * @return 跳转到我的地址页面
     */
    @Transactional
    @PostMapping("updateAddress")
    public String updateAddress(HttpServletRequest request, Address updateAddress, Integer pageNum) {

        //1.获取当前登录用户
        User user = UserUtils.getLoginUser(request);

        //2.设置要修改的地址的用户id
        updateAddress.setUserId(user.getId());

        //3.从数据库获取当前要修改的地址
        Address addressFromData = addressService.getAllAddressById(updateAddress.getId());

        //保证默认地址唯一
        //4.判断当前要修改的地址是不是默认地址
        String type = addressFromData.getType();
        if (type.equals("y")) {

            //2.1 如果是默认地址直接修改
            addressService.updateAddress(updateAddress);
            return "redirect:/manageAddressPage";
        }

        //5.要修改的地址不是默认地址
        //5.1 判断是否修改为默认地址
        if (updateAddress.getType().equals("y")) {

            //如果修改为默认地址 就将数据库中的默认地址改为 非默认
            Address addressDefault = addressService.getAddressByType("y");
            if (addressDefault == null) {

                //说明没有默认地址直接更新即可
                addressService.updateAddress(updateAddress);
            } else {

                //有默认地址
                addressDefault.setType("n");
                addressService.updateAddress(addressDefault);
                addressService.updateAddress(updateAddress);
            }

            return "redirect:/manageAddressPage";
        }

        //5.2 要修改的不是默认地址
        addressService.updateAddress(updateAddress);

        return "redirect:/manageAddressPage?pageNum=" + pageNum;
    }

    @PostMapping("/updateAddressPage")
    public String updateAddress(Address address, Model model, Integer pageNum) {

        model.addAttribute("address", address);
        model.addAttribute("pageNum", pageNum);

        return "address/updateAddress";
    }

    /**
     * 将用户添加的收货地址保存到数据库
     *
     * @param address 用户保存的地址
     * @return 返回确认订单页面
     */
    @PostMapping("/addAddress/{pages}/{total}")
    public String addAddress(HttpServletRequest request, Address address,
                             @PathVariable(value = "total", required = false) Integer total,
                             @PathVariable(value = "pages", required = false) Integer pages) {

        //1. 获取登录的用户
        User user = UserUtils.getLoginUser(request);

        //2. 将改地址添加到对应用户中
        address.setUserId(user.getId());

        //3. 获取该用户其他地址
        List<Address> addresses = addressService.getAllAddressByUserId(user.getId());

        //4. 判断当前用户有没有其他地址
        if (addresses.isEmpty()) {

            //.1 如果没有 将当前地址设置为默认地址
            address.setType("y");
        } else {

            //.2 如果有 判断当前地址是否为默认地址
            if (address.getType().equals("y")) {

                //.2.1 将数据库中的默认地址更新为非默认地址
                for (Address addressFromData : addresses) {

                    if (addressFromData.getType().equals("y")) {

                        addressFromData.setType("n");
                        //更新到数据库
                        addressService.updateAddress(addressFromData);
                        break;
                    }
                }
            }
        }

        //5. 保存到数据库
        addressService.addAddress(address);

        //6.如果将新添加的地址设置为默认地址就跳转到第一页
        if (address.getType().equals("y")) {

            return "redirect:/manageAddressPage?pageNum=1";
        }

        //7.如果不会就跳转到最后一页

        if (total % 6 == 0) {

            return "redirect:/manageAddressPage?pageNum=" + (pages + 1);
        }
        return "redirect:/manageAddressPage?pageNum=" + pages;
    }

    /**
     * @return 跳转到添加地址页面
     */
    @GetMapping("/addAddressPage/{pages}/{total}")
    public String addAddressPage(@PathVariable(value = "pages", required = false) Integer pages,
                                 @PathVariable(value = "total", required = false) Integer total,
                                 Model model) {
        if (pages != null && total != null) {

            model.addAttribute("pages", pages);
            model.addAttribute("total", total);
        }

        return "address/add_receive_address";
    }

    /**
     * @return 跳转到管理地址页面
     */
    @GetMapping("/manageAddressPage")
    public String manageAddressPage(HttpServletRequest request, Model model,
                                    @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {

        //1.获取当前登录用户
        User user = UserUtils.getLoginUser(request);

        //2.获取当前用户的所有地址
        List<Address> allAddresses = addressService.getAllAddressByUserId(user.getId());

        //2.1遍历查看是否有默认地址
        boolean hasDefault = false;
        for (Address address : allAddresses) {

            if (address.getType().equals("y")) {

                hasDefault = true;
                break;
            }
        }

        //2.2如果没有默认地址 提示用户
        if (!hasDefault) {

            model.addAttribute("addressMessage", "暂无默认地址，请设置！！");
        }

        //3. 开始分页 pageNum当前为第几页 每页6条数据
        PageHelper.startPage(pageNum, 6);

        //4.获取用户的地址的当前页数据
        List<Address> addressesPageNum = addressService.getAllAddressByUserId(user.getId());

        //5.获取一个分页对象
        PageInfo<Address> page = new PageInfo<>(addressesPageNum);

        //6.将数据放到request域中 供前端页面展示
        //当前页数据
        model.addAttribute("addresses", addressesPageNum);
        //当前页
        model.addAttribute("pageNum", page.getPageNum());
        //总记录数
        model.addAttribute("total", page.getTotal());
        //当前页记录数
        model.addAttribute("size", page.getSize());
        //总页数
        model.addAttribute("pages", page.getPages());

        return "address/addresses";
    }
}