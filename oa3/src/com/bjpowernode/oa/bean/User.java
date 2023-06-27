package com.bjpowernode.oa.bean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener {
    private String name;
    private String password;

    //用户登录,增加在线人数
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //用户登录,
        // User类型的对象session存放了

        //获取ServletContext对象
        ServletContext application = event.getSession().getServletContext();
        //获取在线人数
        Object onlinecount = application.getAttribute("onlinecount");
        if (onlinecount == null) {
            application.setAttribute("onlinecount", 1);
        } else {
            int count = (Integer) onlinecount;
            count++;
            application.setAttribute("onlinecount", count);
        }

    }

    //用户退出,减少在线人数
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        //用户退出了
        //User类型的对象的session删除了
        ServletContext application = event.getSession().getServletContext();
        Integer onlinecount = (Integer) application.getAttribute("onlinecount");
        onlinecount--;
        application.setAttribute("onlinecount", onlinecount);

    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User{name = " + name + ", password = " + password + "}";
    }
}
