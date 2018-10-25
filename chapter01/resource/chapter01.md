##Struts2简单实例开发

1.下载struts2  
2.建立项目，导入struts2的jar包  
3.配置web.xml  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <filter>
        <filter-name>struts2</filter-name>
        <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>struts2</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!-- Welcome file lists -->
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
</web-app>
```  
4.编写Action类  
```java
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

```  
5.创建并配置struts.xml  
```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
        "http://struts.apache.org/dtds/struts-2.0.dtd">

<struts>
    <package name="default" extends="struts-default">

        <action name="welcome" class="com.controller.FirstAction">
        <result name="ok">/welcome.jsp</result>
        <result name="fail">/index.jsp</result>
        </action>

    </package>
</struts>
```  
6.编写用户登录界面  
```html
<%--
  Created by IntelliJ IDEA.
  User: yangxuechen
  Date: 2018/10/25
  Time: 7:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>

  <form action="welcome.action">
    用户名：<input type="text" name="userName"><br>
    密  码：<input type="password" name="password"><br>
    <input type="submit" value="登陆">
  </form>
  </body>
</html>

```  
如果登录成功，则返回welcome.jsp,内容如下  
```html
<%--
  Created by IntelliJ IDEA.
  User: yangxuechen
  Date: 2018/10/25
  Time: 8:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>welcome</title>
</head>
<body>
<b>欢迎，登录成功!</b>
</body>
</html>

```  
否则返回index.jsp  
7.部署运行  
8.获取源码地址[https://github.com/yangxuechen/ssh-learning-example](https://github.com/yangxuechen/ssh-learning-example)


