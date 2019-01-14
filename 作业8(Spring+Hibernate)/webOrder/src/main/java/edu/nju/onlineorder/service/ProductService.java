package edu.nju.onlineorder.service;

import edu.nju.onlineorder.model.Product;

import java.util.ArrayList;

public interface ProductService {

    public ArrayList<Product> getProductsByCategory(String category);

}
