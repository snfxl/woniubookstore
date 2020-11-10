package com.sn.woniubookstore.service;

import com.sn.woniubookstore.mapper.UserMapper;
import com.sn.woniubookstore.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/21 11:56
 * @Description: TODO
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserByAccount(String username){

        return userMapper.queryUserByAccount(username);
    }

    public void saveUser(User user) {

        userMapper.saveUser(user);
    }

    public void updateUser(User user) {

        userMapper.updateUser(user);
    }

}