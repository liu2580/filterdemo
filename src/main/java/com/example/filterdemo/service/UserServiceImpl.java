package com.example.filterdemo.service;

import com.example.filterdemo.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutianqi
 * @date 2019/11/26
 */

@Service
public class UserServiceImpl implements UserService {

    private static Map<String, User> users = new HashMap<>();

    static {
        users.put("zs", new User(001, "zs", "张三", "zs123"));
        users.put("liu", new User(002, "liu", "刘天琪", "123"));

    }

    @Override
    public User getUserByUsername(String username) {
        return users.get(username);
    }

    @Override
    public List<User> findAllUser() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserbyId(Integer userId) {
        for (String key : users.keySet()) {
            User user = users.get(key);
            if (user.getId().intValue() == userId) {
                return user;
            }
        }
        return null;
    }

}

