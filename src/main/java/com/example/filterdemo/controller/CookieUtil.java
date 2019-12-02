package com.example.filterdemo.controller;

import com.example.filterdemo.dao.User;
import com.example.filterdemo.service.LocalCookieAuthenticator;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import java.time.Instant;

/**
 * @author liutianqi
 * @date 2019/12/2
 */
public class CookieUtil {
    // 服务器密钥
    private static final String SECRET_KEY = "secret";
    // Cookie的组成部分的数目
    private static final short PART_NUM = 3;

    /**
     * 生成cookie
     *
     * @param user
     * @return
     */
    public static Cookie generateCookie(User user) {
        int uid = user.getId();
        String password = user.getPassword();
        long expires = Instant.now().plusSeconds(60).toEpochMilli(); // 60秒过期

        String value = uid + ":" + password + ":" + expires + ":" + SECRET_KEY;

        // 使用ＭＤ５加密，生成签名
        String signature = DigestUtils.md5DigestAsHex(value.getBytes());

        String cookieVal = uid + ":" + expires + ":" + signature;

        return new Cookie(LocalCookieAuthenticator.USER_AUTH_COOKIE, cookieVal);
    }

    /**
     * 验证Cookie合法性
     *
     * @param cookie
     * @return
     */
    public static String[] splitCookie(String cookie) {
        String[] values = cookie.split(":");
        if (values.length != PART_NUM) {
            System.out.println("cookie不合法，可能被篡改");
            return null;
        }

        for (String v : values) {
            if (v == null || "".equals(v)) {
                System.out.println("cookie数据缺失");
                return null;
            }
        }

        return values;
    }

    /**
     * 验证cookie是否过期
     *
     * @param expires
     * @return
     */
    public static boolean validateCookieExpire(long expires) {
        if (expires <= System.currentTimeMillis()) {
            System.out.println("cookie 已过期");
            return false;
        }
        return true;
    }

    public static boolean validateCookieSignature(int userId, long expires, User user, String clientSignature) {
        // 用客户端传过来的数据生成签名
        String value = userId + ":" + user.getPassword() + ":" + expires + ":" + SECRET_KEY;
        String serverSignature = DigestUtils.md5DigestAsHex(value.getBytes());
        System.out.println("server:" + serverSignature);
        System.out.println("client:" + clientSignature);

        // 比较用户上传的签名与生成的签名是否一样
        if (serverSignature.equals(clientSignature)) {
            System.out.println("cookie验证成功");
            return true;
        }
        return false;
    }
}
