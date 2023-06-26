<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%--点击修改,执行${pageContext.request.contextPath}/dept/marge这个servlet--%>
<form action="${pageContext.request.contextPath}/dept/marge" method='post'>
部门编号<input type='text' name='deptno' value='${dept.deptno}' readonly><br>
部门名称<input type='text' name='dname' value='${dept.dname}'><br>
部门位置<input type='text' name='loc' value='${dept.loc}'><br>


<input type='submit' value='修改'>

</form>
</body>

</html>