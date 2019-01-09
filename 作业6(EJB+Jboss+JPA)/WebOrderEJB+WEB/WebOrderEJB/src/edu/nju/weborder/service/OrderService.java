package edu.nju.weborder.service;


import edu.nju.weborder.enums.Results;

import javax.ejb.Remote;
import java.util.HashMap;

@Remote
public interface OrderService {

    public Results payOrder(int uid, HashMap<String, String> list);

    public double getTotalPrice(HashMap<String, String> list);//-1 = wrong input;-2 = not enough stock

}
