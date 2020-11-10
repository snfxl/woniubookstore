package com.sn.woniubookstore.service;

import com.sn.woniubookstore.mapper.AddressMapper;
import com.sn.woniubookstore.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 13:22
 * @Description: TODO
 */
@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    public void addAddress(Address address){

        addressMapper.addAddress(address);
    }

    public List<Address> getAllAddressByUserId(Integer id) {

        return addressMapper.getAllAddressByUserId(id);
    }

    public void updateAddress(Address addressFromData) {

        addressMapper.updateAddress(addressFromData);
    }

    public Address getAddressByType(String type) {

        return addressMapper.getAddressByType(type);
    }

    public Address getAllAddressById(Integer id) {

        return addressMapper.getAllAddressById(id);
    }

    public void deleteAddressByAddressId(Integer addressId) {

        addressMapper.deleteAddressByAddressId(addressId);
    }
}