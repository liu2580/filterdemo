package com.example.filterdemo.service;

import com.example.filterdemo.dao.User;

import java.util.List;

/**
 * @author liutianqi
 * @date 2019/11/26
 */
public interface UserService {
    // 通过username获取用户
    User getUserByUsername(String username);

    // 通过userId获取用户
    User getUserbyId(Integer userId);

    // 查找所有用户
    List<User> findAllUser();

}
