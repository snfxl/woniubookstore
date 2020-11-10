package com.sn.woniubookstore.mapper;

import com.sn.woniubookstore.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/21 11:52
 * @Description: TODO
 */
@Repository
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @param account 用户名
     * @return 用户
     */
    User queryUserByAccount(String account);

    /**
     * 保存用户
     * @param user 用户
     */
    void saveUser(User user);

    /**
     * 更新用户
     * @param user 用户
     */
    void updateUser(User user);
}
