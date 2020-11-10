package com.sn.woniubookstore.mapper;

import com.sn.woniubookstore.pojo.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 13:23
 * @Description: TODO
 */
@Repository
public interface AddressMapper {

    /**
     * 添加地址
     * @param address 地址
     */
    void addAddress(Address address);

    /**
     * 获取当前用户的所有地址
     * @param userId 用户id
     * @return 获取当前用户的所有地址
     */
    List<Address> getAllAddressByUserId(Integer userId);

    /**
     * 更新地址
     * @param addressFromData 要更新的addressFromData
     */
    void updateAddress(Address addressFromData);

    /**
     * 获取数据库中的默认地址
     * @param type 默认地址类型
     * @return 返回默认地址
     */
    Address getAddressByType(String type);

    /**
     * 通过地址id获取地址
     * @param id id
     * @return 返回查到的地址
     */
    Address getAllAddressById(Integer id);

    /**
     * 通过地址id删除地址
     * @param addressId 地址id
     */
    void deleteAddressByAddressId(Integer addressId);
}