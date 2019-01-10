package edu.nju.onlineorder.dao;

import edu.nju.onlineorder.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductDao extends BaseDao{

    public Product find(int pid);

    public ArrayList<Product> findProductsByCategory(String cname);

    public void updateStock(HashMap<String, String> list);

}
