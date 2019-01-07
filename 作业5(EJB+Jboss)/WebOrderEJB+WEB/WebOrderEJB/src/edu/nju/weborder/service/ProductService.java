package edu.nju.weborder.service;


import edu.nju.weborder.model.Product;

import javax.ejb.Remote;
import java.util.ArrayList;

@Remote
public interface ProductService {

    public ArrayList<Product> getProductsByCategory(String category);

}
