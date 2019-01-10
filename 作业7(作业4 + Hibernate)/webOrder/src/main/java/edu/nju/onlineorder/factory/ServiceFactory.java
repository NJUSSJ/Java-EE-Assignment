package edu.nju.onlineorder.factory;

import edu.nju.onlineorder.service.OrderService;
import edu.nju.onlineorder.service.ProductService;
import edu.nju.onlineorder.service.UserService;
import edu.nju.onlineorder.service.impl.OrderServiceImpl;
import edu.nju.onlineorder.service.impl.ProductServiceImpl;
import edu.nju.onlineorder.service.impl.UserServiceImpl;

public class ServiceFactory {
    public static ProductService getProductService(){
        return ProductServiceImpl.getProductService();
    }
    public static UserService getUserService(){
        return UserServiceImpl.getUserService();
    }
    public static OrderService getOrderService(){
        return OrderServiceImpl.getOrderService();
    }
}
