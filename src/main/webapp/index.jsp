<%--
  Created by IntelliJ IDEA.
  User: ZeDongW
  Date: 2019/7/16
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Java上传与下载</title>
</head>
<body>
    <div align="center">
        <a href="${pageContext.request.contextPath}/upload.jsp">文件上传</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/ud?method=downList">文件下载</a>
    </div>
</body>
</html>
