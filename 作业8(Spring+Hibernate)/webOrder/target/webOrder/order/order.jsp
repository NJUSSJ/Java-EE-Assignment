<%@ page import="edu.nju.onlineorder.model.Product" %><%--
  Created by IntelliJ IDEA.
  User: njuss
  Date: 2018/12/26
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="order" uri="/WEB-INF/tlds/webOrder.tld" %>

<order:checkSession request="<%=request%>" response="<%=response%>"/>

<html>
<head>
    <title>Online Order</title>
</head>
<body>
    <table width="650" border="0" >
        <tr>
            <td width="650" height="80" background=<%=request.getContextPath() + "/image/top.jpg"%>>&nbsp;</td>
        </tr>
    </table>
    <p>Here is our products: </p>
    <a href=<%=request.getContextPath() + "/Order?category=食品"%>>食品</a>
    <a href=<%=request.getContextPath() + "/Order?category=药物"%>>药物</a>

    <jsp:useBean id="products" type="edu.nju.onlineorder.action.business.ProductListBean" scope="session"/>
    <%pageContext.setAttribute("product", products.getProducts().get(0));%>
    <jsp:useBean id="product" type="edu.nju.onlineorder.model.Product"/>
    <form method="post" action=<%=request.getContextPath() + "/Place"%>>
        <table>
            <tr>
                <td width=50>名称</td>
                <td width=80>库存数量</td>
                <td width=50>价格</td>
                <td width=50>种类</td>
                <td width=80>购买数量</td>
            </tr>

            <%
                for(int i = 0; i < products.getProducts().size(); i++){
                    pageContext.setAttribute("product", products.getProducts().get(i));

            %>
            <tr>
                <td width=50><jsp:getProperty name="product" property="pname"/></td>
                <td width=80><jsp:getProperty name="product" property="stocknum"/></td>
                <td width=50><jsp:getProperty name="product" property="price"/></td>
                <td width=50><jsp:getProperty name="product" property="category"/></td>
                <td width=80><input width="20" type="text" name=<%=((Product)pageContext.getAttribute("product")).getPid()%>></td>
            </tr>

            <%}%>
        </table>
        <br>
        <input type="submit" name="Place" value="下单"/>
    </form>

    <form method="post" action=<%=request.getContextPath() + "/Logout"%>>
        <input type="submit" name="Logout" value="登出">
    </form>
</body>
</html>
