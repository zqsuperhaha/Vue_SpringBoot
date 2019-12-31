package com.zq.mywork.intercepter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIntercepter implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 定义 session
        HttpSession session = request.getSession();

        // 确定拦截的路径
        String contextPath = session.getServletContext().getContextPath();
        String[] requireAuthPages = new String[]{ "admin" };
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath+"/");
        String page = uri;

        // 拦截
        if(begingWith(page,requireAuthPages)){
            String id = (String)session.getAttribute("id");
            if(id==null){
                System.out.println("拦截成功！");
                response.sendRedirect("login");
                return false;
            }
        }


        return true;
    }

    // 判断路径是否要拦截
    private boolean begingWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if(StringUtils.startsWith(page, requiredAuthPage)) {
                result = true;
                break;
            }
        }
        return result;
    }



}
