package com.controller;

import com.opensymphony.xwork2.ActionSupport;

public class FirstAction extends ActionSupport {

    //用来接收表单提交的数据，需要get,set方法
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //重写execute方法
    @Override
    public String execute() throws Exception {

        //模拟数据库查询，验证用户名及密码
        if(userName.equals("LeBron")){
            if(password.equals("123456")){
                return "ok";
            }else{
                return "fail";
            }
        }else{
            return "fail";
        }
    }
}
