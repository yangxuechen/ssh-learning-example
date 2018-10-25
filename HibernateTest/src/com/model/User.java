package com.model;

public class User {
    private int userId;  //用户编号
    private String userName; //用户名
    private String userPwd;  //密码
    private String userEmail;//邮箱

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
