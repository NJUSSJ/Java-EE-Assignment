package edu.nju.onlineorder.service;

import edu.nju.onlineorder.enums.Results;

import java.util.HashMap;

public interface OrderService {

    public Results payOrder(int uid, HashMap<String, String> list);

    public double getTotalPrice(HashMap<String, String> list);//-1 = wrong input;-2 = not enough stock

}
