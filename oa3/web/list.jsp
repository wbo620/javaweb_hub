<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang='en'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>列表</title>
<%--    <base href="http://localhost:8080/oa/">--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">

    <script type='text/javascript'>
        function del(dno) {
            if (window.confirm('亲，删了不可恢复哦！')) {
                document.location.href = '${pageContext.request.contextPath}/dept/delete?deptno=' + dno
            }
        }
    </script>
</head>

<body>
<h3>在线人数:${onlinecount}</h3>

<p>欢迎</p>
<p><a href="user/exit">安全退出</a></p>

<h1 align="center">部门列表</h1>
<hr>
<table border='1px' align='center' width='50%'>
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
    </tr>

    <c:forEach items="${deptList}" varStatus="deptStatus" var="dept">

        <tr>
            <td>${deptStatus.count}
            </td>
            <td>${dept.deptno}
            </td>
            <td>${dept.dname}
            </td>
            <td>
                <a href='javascript:void(0)' onclick='del(${dept.deptno})'>删除</a>
                <a href='dept/edit?deptno=${dept.deptno}'>修改</a>
                <a href='dept/detail?deptno=${dept.deptno}'>详情</a>
            </td>
        </tr>

    </c:forEach>


</table>
<hr>
<a href='add.jsp'> 新增部门</a>


</body>

</html>