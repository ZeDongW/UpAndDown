<%--
  Created by IntelliJ IDEA.
  User: ZeDongW
  Date: 2019/7/17
  Time: 7:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件下载</title>
</head>
<body>
    <table border="1" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <th>序号</th>
            <th>文件名</th>
            <th>操作</th>
        </tr>
        <c:if test="${not empty requestScope.fileNames}">
            <c:forEach var="file" items="${requestScope.fileNames}" varStatus="vs">
                <tr>
                    <td>${vs.count}</td>
                    <td>${file.value}</td>
                    <td>
                        <c:url var="url" value="ud">
                            <c:param name="method" value="down"/>
                            <c:param name="fileName" value="${file.key}"/>
                        </c:url>
                        <a href="${url}">下载</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</body>
</html>
