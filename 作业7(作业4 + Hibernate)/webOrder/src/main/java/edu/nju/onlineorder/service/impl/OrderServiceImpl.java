package edu.nju.onlineorder.service.impl;

import edu.nju.onlineorder.dao.ProductDao;
import edu.nju.onlineorder.dao.UserDao;
import edu.nju.onlineorder.enums.Results;
import edu.nju.onlineorder.factory.DaoFactory;
import edu.nju.onlineorder.model.Product;
import edu.nju.onlineorder.model.User;
import edu.nju.onlineorder.service.OrderService;

import java.util.HashMap;

public class OrderServiceImpl implements OrderService {
    private static UserDao userDao = DaoFactory.getUserDao();
    private static ProductDao productDao = DaoFactory.getProductDao();

    private static OrderService orderService = new OrderServiceImpl();

    private OrderServiceImpl(){

    }

    public static OrderService getOrderService(){
        if(orderService == null){
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    @Override
    public Results payOrder(int uid, HashMap<String, String> list) {

        double total_price = getTotalPrice(list);
        if(total_price == -1){
            return Results.NOT_ENOUGH_STOCK;
        }else if (total_price == -2){
            return Results.WRONG_INPUT;
        }

        User user = userDao.find(uid);
        if(total_price > user.getDeposit()){
            return Results.NOT_ENOUGH_DEPOSIT;
        }

        if((total_price / 0.8) >= 100){
            productDao.updateStock(list);
            userDao.updateDeposit(uid, user.getDeposit() - total_price);
            return Results.DISCOUNT;
        }

        productDao.updateStock(list);
        userDao.updateDeposit(uid, user.getDeposit() - total_price);

        return Results.SUCCESS;
    }

    @Override
    public double getTotalPrice(HashMap<String, String> list) {
        double total_price = 0;
        for (String pid: list.keySet()
             ) {
            Product product = productDao.find(Integer.parseInt(pid));
            int num = 0;
            try{
                num= Integer.parseInt(list.get(pid));
                if(num > product.getStocknum()){
                    return -1;
                }
                total_price += num * product.getPrice();
            }catch (NumberFormatException e){
                return -2;
            }
        }
        if(total_price > 100){
            total_price *= 0.8;
        }
        return total_price;
    }
}
