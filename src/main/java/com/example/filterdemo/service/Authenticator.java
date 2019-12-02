package com.example.filterdemo.service;

import com.example.filterdemo.dao.User;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutianqi
 * @date 2019/12/2
 */
public interface Authenticator {
    // 认证成功返回User，认证失败抛出异常，无认证信息返回null:
    User authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticateException;

}
