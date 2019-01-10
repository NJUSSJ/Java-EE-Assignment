package edu.nju.onlineorder.servlet;

import edu.nju.onlineorder.action.business.ProductListBean;
import edu.nju.onlineorder.enums.Results;
import edu.nju.onlineorder.factory.ServiceFactory;
import edu.nju.onlineorder.service.OrderService;
import edu.nju.onlineorder.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Place")
public class PlaceOrderServlet extends HttpServlet {

    private OrderService orderService = ServiceFactory.getOrderService();
    private ProductService productService = ServiceFactory.getProductService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Integer uid = (Integer) session.getAttribute("uid");

        HashMap<String, String> list = new HashMap<>();
        Map map = req.getParameterMap();
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String pid = (String) entry.getKey();
            String num = ((String[]) entry.getValue())[0];
            if(num != null && !num.equals("") && !pid.equals("Place")){
                list.put(pid, num);
            }
        }
        if(session.getAttribute("placed") != null && list.size() == 0){
            getServletContext().getRequestDispatcher("/Order").forward(req, resp);
            return;
        }

        Results result = orderService.payOrder(uid, list);

        switch (result){
            case SUCCESS:
                session.setAttribute("alert", "消费成功！");
                session.setAttribute("total", orderService.getTotalPrice(list));
                break;
            case DISCOUNT:
                session.setAttribute("alert", "享受八折优惠后，");
                session.setAttribute("total", orderService.getTotalPrice(list));
                break;
            case WRONG_INPUT:
                session.setAttribute("alert", "您的输入有误！");
                break;
            case NOT_ENOUGH_STOCK:
                session.setAttribute("alert", "您要购买的商品库存不足！");
                break;
            case NOT_ENOUGH_DEPOSIT:
                session.setAttribute("alert", "您的余额不足！");
        }

        ProductListBean productListBean = new ProductListBean();
        productListBean.setProducts(productService.getProductsByCategory((String) session.getAttribute("category")));
        session.setAttribute("products", productListBean);

        getServletContext().getRequestDispatcher("/order/orderResult.jsp").forward(req, resp);


    }
}
