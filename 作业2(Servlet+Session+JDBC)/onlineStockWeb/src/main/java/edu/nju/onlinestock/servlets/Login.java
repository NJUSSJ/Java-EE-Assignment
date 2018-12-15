package edu.nju.onlinestock.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/Login")
public class Login extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = "";
        HttpSession session = req.getSession(false);
        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();

        if(cookies != null){
            for (Cookie c: cookies
                 ) {
                cookie = c;
                if(cookie.getName().equals("LoginCookie")){
                    login = cookie.getValue();
                    break;
                }
            }
        }

        // Logout action removes session, but the cookie remains
        if (null != req.getParameter("Logout")) {
            if (null != session) {
                session.invalidate();
                session = null;
            }
        }

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        out.println(
                "<form method='POST' action='"
                        + resp.encodeURL(req.getContextPath()+"/PlaceMyOrder")
                        + "'>");
        out.println(
                "login: <input type='text' name='login' value='" + login + "'>");
        out.println(
                "password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Submit' value='Submit'>");

        if(null != req.getParameter("failure")){
            out.println("<p style=\"color: red\">用户名或密码错误！</p>");
        }

        out.println("<p>Servlet is version @version@</p>");
    }
}
