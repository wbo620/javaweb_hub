<%@ page import="java.util.List" %>
<%@ page import="com.bjpowernode.oa.bean.Dept" %>

<%@page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang='en'>

<head>
    <%-- <meta charset='UTF-8'>--%>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <title>列表</title>

    <script type='text/javascript'>
        function del(dno) {
            if (window.confirm('亲，删了不可恢复哦！')) {
                document.location.href = '<%=request.getContextPath()%>/dept/delete?deptno=' + dno
            }
        }
    </script>
</head>

<body>
<table border='1px' align='center' width='50%'>
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
    </tr>

    <%

        int i = 0;
        List<Dept> deptList = (List<Dept>) request.getAttribute("deptList");
        for (Dept dept : deptList) {
            String deptno = dept.getDeptno();
            String dname = dept.getDname();
    %>
    <tr>
        <td><%=++i%>
        </td>
        <td><%=deptno%>
        </td>
        <td><%=dname%>
        </td>
        <td>
            <a href='javascript:void(0)' onclick='del(<%=deptno%>)'>删除</a>
            <a href='<%=request.getContextPath()%>/dept/edit?deptno=<%=deptno%>'>修改</a>
            <a href='<%=request.getContextPath()%>/dept/detail?deptno=<%=deptno%>'>详情</a>
        </td>
    </tr>


    <%
        }
    %>


</table>
<hr>
<a href='<%= request.getContextPath()%>/add.jsp'> 新增部门</a>


</body>

</html>