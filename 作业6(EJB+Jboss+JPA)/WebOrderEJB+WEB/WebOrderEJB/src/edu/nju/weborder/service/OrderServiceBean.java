package edu.nju.weborder.service;

import edu.nju.weborder.dao.ProductDao;
import edu.nju.weborder.dao.UserDao;
import edu.nju.weborder.enums.Results;
import edu.nju.weborder.model.Product;
import edu.nju.weborder.model.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;

@Stateless
public class OrderServiceBean implements OrderService{

    @EJB
    private UserDao userDao;

    @EJB
    private ProductDao productDao;

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
