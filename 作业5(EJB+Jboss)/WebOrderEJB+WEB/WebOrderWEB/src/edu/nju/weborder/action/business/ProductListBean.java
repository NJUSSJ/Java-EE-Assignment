package edu.nju.weborder.action.business;


import edu.nju.weborder.model.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductListBean implements Serializable {
    private ArrayList<Product> products;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
