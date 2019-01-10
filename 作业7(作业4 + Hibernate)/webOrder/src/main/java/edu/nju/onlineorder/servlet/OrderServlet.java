package edu.nju.onlineorder.servlet;

import edu.nju.onlineorder.Util.SessionContext;
import edu.nju.onlineorder.action.business.ProductListBean;
import edu.nju.onlineorder.factory.ServiceFactory;
import edu.nju.onlineorder.service.ProductService;
import edu.nju.onlineorder.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/Order")
public class OrderServlet extends HttpServlet {

    private UserService userService = ServiceFactory.getUserService();
    private ProductService productService = ServiceFactory.getProductService();
    public static String defaultCategoty = "食品";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        boolean cookieFound = false;
        System.out.println(req.getParameter("login") + " request");
        Cookie cookie = null;

        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(int i = 0; i < cookies.length; i++){
                cookie = cookies[i];
                if(cookie.getName().equals("LoginCookie")){
                    cookieFound = true;
                    break;
                }
            }
        }

        if(session == null){
            String loginValue = req.getParameter("login");
            boolean isLoginAction = !(loginValue == null);

            System.out.println(loginValue + " session null");
            if(isLoginAction){
                if(cookieFound){
                    if(!loginValue.equals(cookie.getValue())){
                        cookie.setValue(loginValue);
                        resp.addCookie(cookie);
                    }
                }else{
                    cookie = new Cookie("LoginCookie", loginValue);
                    cookie.setMaxAge(Integer.MAX_VALUE);
                    resp.addCookie(cookie);
                }

                Integer uid = userService.isValidUser(req.getParameter("login"), req.getParameter("password"));
                if(uid != null){
                    //异地登录冲突
                    HashMap<String, HttpSession> sessionMap = SessionContext.sessionMap;
                    HttpSession sessionToBeDelete = null;
                    for (String sessionID: sessionMap.keySet()
                         ) {
                        HttpSession tmp = sessionMap.get(sessionID);
                        if(tmp.getAttribute("uid") != null && tmp.getAttribute("uid").equals(uid)){
                            sessionToBeDelete = tmp;
                            break;
                        }
                    }

                    if (sessionToBeDelete != null){
                        sessionToBeDelete.invalidate();
                    }

                    session = req.getSession(true);
                    session.setMaxInactiveInterval(600);
                    session.setAttribute("login", loginValue);
                    session.setAttribute("uid", uid);

                    req.setAttribute("uid", uid);
                    //getMainPage
                    showOrder(req, resp);
                }else{
                    //find no match user
                    System.out.println("no match user");
                    resp.sendRedirect(req.getContextPath() + "/login/loginFail.html");
                }
            }else{
                System.out.println(loginValue + " session null");
                // Display the login page. If the cookie exists, set login
                resp.sendRedirect(req.getContextPath() + "/login/login.jsp");
            }
        }else{
            //session exists
            String loginValue = (String) session.getAttribute("login");
            Integer uid = (Integer) session.getAttribute("uid");

            System.out.println(loginValue + " session");

            req.setAttribute("uid", uid);
            showOrder(req, resp);
        }
    }

    private void showOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(true);
        ServletContext context = getServletContext();

        ProductListBean productListBean = new ProductListBean();
        String category = request.getParameter("category");
        session.setAttribute("category", category);
        if(category == null){
            session.setAttribute("category", OrderServlet.defaultCategoty);
            productListBean.setProducts(productService.getProductsByCategory(OrderServlet.defaultCategoty));
        }else {
            productListBean.setProducts(productService.getProductsByCategory(category));
        }

        try{
            session.setAttribute("products", productListBean);
            context.getRequestDispatcher("/order/order.jsp").forward(request, response);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }
}
