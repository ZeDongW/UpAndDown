<%--
  Created by IntelliJ IDEA.
  User: ZeDongW
  Date: 2019/7/16
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
    <div align="center" >
        <form action="${pageContext.request.contextPath}/ud?method=upload" method="post" enctype="multipart/form-data">
            文件：<input type="file" name="file"/><br/><br/>
            <input type="submit" value="提交">
        </form>
    </div>
</body>
</html>
