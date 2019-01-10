<%--
  Created by IntelliJ IDEA.
  User: njuss
  Date: 2018/12/26
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>

<%
    ServletContext context = pageContext.getServletContext();
    int webCounter = Integer.parseInt((String) context.getAttribute("webCounter"));
    webCounter++;
    context.setAttribute("webCounter", Integer.toString(webCounter));
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Online Order</title>
</head>
<body>
<table width="650" border="0" >
    <tr>
        <td width="650" height="80" background="../image/top.jpg">&nbsp;</td>
    </tr>
</table>

<form action="../Order" method="POST">
    <table>
        <tr><td>用户名:</td><td><INPUT type="text" name="login" /></td></tr>
        <tr><td>密码:</td><td><INPUT type="password" name="password"/></td></tr>
        <tr><td><INPUT type="submit" value="登录"></td><td><input type="reset" value="重置"></td></tr>
    </table>
</form>

<form action="../visit/visit.jsp" method="post">
    <input type="submit" name="visit" value="游客访问">
</form>
</body>
</html>
