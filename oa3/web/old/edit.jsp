<%@ page import="com.bjpowernode.oa.bean.Dept" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang='en'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>修改部门</title>
</head>

<body>
<h1>修改部门</h1>
<hr>
<%-- Dept dept = (Dept) request.getAttribute("dept");--%>
<form action='<%=request.getContextPath()%>/dept/marge' method='post'>
    <%

        List<Dept> deptList =(List<Dept>) request.getAttribute("deptList");
        for (Dept dept : deptList) {
            String deptno = dept.getDeptno();
            String dname = dept.getDname();
            String loc = dept.getLoc();

    %>
    部门编号<input type='text' name='deptno' value='<%=deptno %>' readonly><br>
    部门名称<input type='text' name='dname' value='<%=dname %>'><br>
    部门位置<input type='text' name='loc' value='<%=loc %>'><br>
    <%
        }
    %>
    <input type='submit' value='修改'>

</form>
</body>

</html>