package edu.nju.onlinestock.servlets;

import edu.nju.onlinestock.models.Product;
import edu.nju.onlinestock.models.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

@WebServlet("/PlaceMyOrder")
public class PlaceMyOrder extends HttpServlet {

    private DataSource dataSource = null;
    private int state = 0;
    private double total_price = 0;

    @Override
    public void init() {
        InitialContext ctx;

        Properties properties = new Properties();
        properties.put(Context.PROVIDER_URL, "jnp:///");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        try{
            ctx = new InitialContext(properties);
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/onlinestock");
        }catch (NamingException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");


        HttpSession session = req.getSession(false);
        boolean cookieFound = false;
        System.out.println(req.getParameter("login") + " req");
        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();

        if(cookies != null){
            for (Cookie c: cookies
                 ) {
                cookie = c;
                if(cookie.getName().equals("LoginCookie")){
                    cookieFound = true;
                    break;
                }
            }
        }

        if(session == null){
            String loginValue = req.getParameter("login");
            boolean isLoginAction = (loginValue != null);

            System.out.println(loginValue + " session null");

            if(isLoginAction){
                //user is logging in
                if(cookieFound){
                    //update the cookie if changed
                    if(!loginValue.equals(cookie.getValue())){
                        cookie.setValue(loginValue);
                        resp.addCookie(cookie);
                    }
                }else{
                    //create a cookie and set the value
                    cookie = new Cookie("LoginCookie", loginValue);
                    cookie.setMaxAge(Integer.MAX_VALUE);
                    resp.addCookie(cookie);
                }

                if(isValidLogin(req)){
                    session = req.getSession(true);
                    session.setAttribute("login", loginValue);
                    session.setAttribute("uid", req.getAttribute("uid"));
                    req.setAttribute("login", loginValue);

                    getProductList(req);
                    displayOrderPage(req, resp);
                    displayLogoutPage(req, resp);
                }else{
                    resp.sendRedirect(req.getContextPath() + "/Login?failure=failure");
                }

            }else {
                resp.sendRedirect(req.getContextPath() + "/Login");
            }
        }else{
            //session exists

            //Handle Order Placement
            if(req.getParameter("Place") != null){
                Connection connection = null;
                PreparedStatement statement = null;
                PreparedStatement statement1 = null;
                PreparedStatement statement2 = null;
                PreparedStatement statement3 = null;
                ResultSet resultSet;

                double deposit = 0;

                try{
                    connection = dataSource.getConnection();
                    statement = connection.prepareStatement("select * from products where pid = ?");
                    statement1 = connection.prepareStatement("select deposit from users where uid = ?");
                    statement1.setString(1, session.getAttribute("uid").toString());
                    resultSet = statement1.executeQuery();
                    while(resultSet.next()){
                        deposit = resultSet.getDouble("deposit");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

                total_price = 0;
                Map map = req.getParameterMap();
                Iterator it =map.entrySet().iterator();
                Map<Integer,Integer> resultMap = new HashMap<>();


                while(it.hasNext()){
                    Map.Entry entry = (Map.Entry)it.next();
                    try {
                        statement.setString(1, entry.getKey().toString());
                        resultSet = statement.executeQuery();

                        while(resultSet.next()){
                            String orderNumStr = ((String[])entry.getValue())[0];
                            Pattern pattern = Pattern.compile("[0-9]*");
                            if((!pattern.matcher(orderNumStr).matches())){
                                state = 1;
                                resultMap.clear();
                                break;
                            }else{
                                int orderNum;
                                if(orderNumStr.equals("")){
                                    orderNum = 0;
                                }else{
                                    orderNum = Integer.parseInt(orderNumStr);
                                }

                                if(orderNum > resultSet.getInt("stocknum")){
                                    state = 2;
                                    resultMap.clear();
                                    break;
                                }else{
                                    total_price += (orderNum * resultSet.getDouble("price"));
                                    resultMap.put(Integer.parseInt(entry.getKey().toString()), orderNum);
                                    state = 3;
                                }
                            }

                        }

                        if(state == 1 || state == 2){
                            break;
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

                if(deposit < total_price && state == 3){
                    state = 4;
                }

                if(total_price >= 100 && state == 3){
                    state = 5;
                    total_price *= 0.8;
                }

                //update the deposit and stock

                try{
                    connection = dataSource.getConnection();
                    statement2 = connection.prepareStatement("update products set stocknum=stocknum - ? where pid = ?");
                    statement3= connection.prepareStatement("update users set deposit=deposit - ? where uid = ?");

                    for (Integer key: resultMap.keySet()
                         ) {
                        statement2.setString(1, resultMap.get(key).toString());
                        statement2.setString(2, key.toString());
                        statement2.addBatch();
                    }
                    statement2.executeBatch();

                    statement3.setString(1, Double.toString(total_price));
                    statement3.setString(2, session.getAttribute("uid").toString());
                    statement3.execute();
                }catch (SQLException e){
                    e.printStackTrace();
                }


            }

            //获取session内容
            String loginValue = (String) session.getAttribute("login");
            Integer uid = (int) session.getAttribute("uid");
            System.out.println(loginValue + " session");

            req.setAttribute("login", loginValue);
            req.setAttribute("uid", uid);

            getProductList(req);
            displayOrderPage(req ,resp);
            displayLogoutPage(req, resp);
        }
    }

    private boolean isValidLogin(HttpServletRequest request){
        String uname = request.getParameter("login");
        String passwd = request.getParameter("password");
        User user = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            statement = connection.prepareStatement("select * from users where uname = ? and password = ?;");
            statement.setString(1, uname);
            statement.setString(2, passwd);

            resultSet = statement.executeQuery();

            while(resultSet.next()){
                user = new User(resultSet.getInt("uid"), resultSet.getString("uname"), resultSet.getString("password"), resultSet.getDouble("deposit"));
            }

            if(user != null){
                System.out.println("user is  not null!");
                request.setAttribute("uid", user.getUid());
                return true;
            }else{
                System.out.println("user is null!");
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private void getProductList(HttpServletRequest request){
        String category = request.getParameter("category");
        if(category == null || category.equals("")){
            category = "食品";
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Product> list = new ArrayList<>();
        Product product;

        try {
            connection = dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            statement = connection.prepareStatement("select * from products where cname = ?");
            statement.setString(1, category);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                product = new Product(resultSet.getInt("pid"), resultSet.getString("pname"), resultSet.getInt("stocknum"), resultSet.getDouble("price"), resultSet.getString("cname"));
                list.add(product);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        request.setAttribute("list", list);
    }

    private void displayOrderPage(HttpServletRequest request, HttpServletResponse response){
        ArrayList<Product> list = (ArrayList<Product>) request.getAttribute("list");

        try {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");

            switch (state){
                case 0:
                    writer.println("<p>Welcome " + request.getAttribute("login") + "!</p>");
                    break;
                case 1:
                    writer.println("<p style = \"color: red\">请正确输入购买数量！</p>");
                    state = 0;
                    break;
                case 2:
                    writer.println("<p style = \"color: red\">库存不足！</p>");
                    state = 0;
                    break;
                case 3:
                    writer.println("<p style = \"color: red\">购买成功， 您一共消费" + total_price + "元</p>");
                    state = 0;
                    break;
                case 4:
                    writer.println("<p style = \"color: red\">您的余额不足！</p>");
                    state = 0;
                    break;

                case 5:
                    writer.println("<p style = \"color: red\">购买成功， 您在享受八折优惠的情况下一共消费" + total_price + "元</p>");


            }



            writer.println("<p>Here is our products: </p>");

            writer.println("<a href = " + request.getContextPath() + "/PlaceMyOrder?category=食品>食品</a>");
            writer.println("<a href = " + request.getContextPath() + "/PlaceMyOrder?category=药物>药物</a><br>");

            writer.println("<form method='POST' action='" + request.getContextPath() + "/PlaceMyOrder" + "'>" );

            writer.println("<table>");
            writer.println("<tr>");
            writer.println("<td width=50>名称</td>");
            writer.println("<td width=80>库存数量</td>");
            writer.println("<td width=50>价格</td>");
            writer.println("<td width=50>种类</td>");
            writer.println("<td width=20>购买数量</td>");
            writer.println("<tr/>");

            for (Product product: list
                 ) {

                int pid = product.getPid();
                String pname = product.getPname();
                int stocknum = product.getStocknum();
                double price = product.getPrice();
                String category = product.getCategory();

                writer.println("<tr>");
                writer.println("<td width=50>" + pname + "</td>");
                writer.println("<td width=80>" + stocknum + "</td>");
                writer.println("<td width=50>" + price + "</td>");
                writer.println("<td width=50>" + category + "</td>");
                writer.println("<td width=20><input type=\"text\" name=\"" + pid + "\" width=20></td>");
                writer.println("<tr/>");

            }

            writer.println("</table>");
            writer.println("<input type='submit' name='Place' value='Place'>");
            writer.println("</form>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayLogoutPage(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.println("<form method='GET' action='" + request.getContextPath() + "/Login" + "'>");
        writer.println("</p>");
        writer.println("<input type='submit' name='Logout' value='Logout'>");
        writer.println("</form>");
        writer.println("<p>Servlet is version @version@</p>");
        writer.println("</body></html>");
    }
}
