package edu.nju.weborder.dao;


import edu.nju.weborder.model.Product;

import javax.ejb.Remote;
import java.util.ArrayList;
import java.util.HashMap;

@Remote
public interface ProductDao {

    public Product find(int pid);

    public ArrayList<Product> findProductsByCategory(String cname);

    public void updateStock(HashMap<String, String> list);

}
