package com.example.filterdemo.filter;

import com.example.filterdemo.dao.User;
import com.example.filterdemo.service.AuthenticateException;
import com.example.filterdemo.service.Authenticator;
import com.example.filterdemo.service.LocalCookieAuthenticator;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutianqi
 * @date 2019/12/2
 */
@WebFilter(filterName = "SigninFilter", urlPatterns = {"/index", "/users"})
public class SigninFilter implements Filter {

    private Authenticator[] authenticators = initAuthenticators();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    private Authenticator[] initAuthenticators() {
        Authenticator[] authenticators = new Authenticator[] {new LocalCookieAuthenticator()};
        return authenticators;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("start filter................");
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        User user = tryGetAuthenticatedUser(req, resp);

        if (user == null) {
            resp.sendRedirect("/signin");
            return;
        }

      //  try (UserContext context = new UserContext(user)) {
            chain.doFilter(request, response);
    //    }

    }

    public User tryGetAuthenticatedUser(HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        for (Authenticator authenticator : authenticators) {
            try {
                user = authenticator.authenticate(request, response);
            } catch (AuthenticateException e) {
            }
            if (user != null) {
                break;
            }
        }
        return user;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
