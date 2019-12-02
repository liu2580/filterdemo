package com.example.filterdemo.service;

import com.example.filterdemo.controller.CookieUtil;
import com.example.filterdemo.dao.User;
import com.example.filterdemo.util.ApplicationContextUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutianqi
 * @date 2019/12/2
 */
public class LocalCookieAuthenticator implements Authenticator {

    public static final String USER_AUTH_COOKIE = "UserInfo";


    @Override
    public User authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticateException {
        String cookie = getCookieFromRequest(request, USER_AUTH_COOKIE);
        if (cookie == null) {
            return null;
        }
        return getUserByCookie(cookie);
    }

    // 从request中获取指定名称的Cookie
    private String getCookieFromRequest(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * 通过cookie查找用户
     *
     * @param cookie
     * @return
     */
    private User getUserByCookie(String cookie) throws AuthenticateException {

        // 验证cookie的格式是否符合规范
        String[] values = CookieUtil.splitCookie(cookie);
        if (values == null) {
            throw new AuthenticateException(10001, "认证失败");
        }

        // 验证数据的合法性
        Integer userId = Integer.parseInt(values[0]);
        long expires = Long.parseLong(values[1]);
        String clientSignature = values[2];

        // cookie 过期
        if (!CookieUtil.validateCookieExpire(expires)) {
            throw new AuthenticateException(10002, "会话过期");
        }

        // 获取用户
        // 通过ApplicationContextUtil注入userService
        UserService userService = ApplicationContextUtil.getBean(UserService.class);
        User user = userService.getUserbyId(userId);
        if (user == null) {
            System.out.println("没有此用户");
            throw new AuthenticateException(10003, "认证失败");
        }

        // 验证签名
        if (!CookieUtil.validateCookieSignature(userId, expires, user, clientSignature)) {
            System.out.println("cookie验证失败");
            throw new AuthenticateException(10004, "认证失败");
        }

        return user;
    }

}