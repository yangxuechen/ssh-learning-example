<%--
  Created by IntelliJ IDEA.
  User: yangxuechen
  Date: 2018/12/11
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
   <s:form action="usertest" method="post">
     <input type="text" name="userName">
       <input type="password" name="password">
       <input type="submit" value="提交">
   </s:form>

</body>
</html>
