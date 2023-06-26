<%@ page import="com.bjpowernode.oa.bean.Dept" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>部门详情</title>
</head>
<body>
<h1> 部门详情</h1>
<hr>

<%
    List<Dept> deptList = (List<Dept>) request.getAttribute("deptList");
    for (Dept dept : deptList) {
        String deptno = dept.getDeptno();
        String dname = dept.getDname();
        String loc = dept.getLoc();
%>
部门编号:<%=deptno%> <br>
部门名称:<%=dname%><br>
部门位置:<%=loc%><br>
<%
    }
%>
<input type="button" value="后退" onclick="window.history.back()"/>

</body>

</html>