<%--
  Created by IntelliJ IDEA.
  User: hallen
  Date: 2023/6/20
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>登录界面</title>
</head>

<body>

登录界面
<form method="post" action="<%=request.getContextPath()%>/user/login">
  用户名: <input type="text" name="username"/><br>
  密码: <input type="password" name="password"/><br>

  <input type="checkbox" name="f" value="1">十天内免登录
  <br>

  <input type="submit" value="登录">

</form>
</body>

</html>
