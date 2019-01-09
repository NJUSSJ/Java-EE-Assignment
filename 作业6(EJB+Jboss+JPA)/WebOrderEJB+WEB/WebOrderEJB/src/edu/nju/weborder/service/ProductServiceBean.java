package edu.nju.weborder.service;


import edu.nju.weborder.dao.ProductDao;
import edu.nju.weborder.model.Product;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;

@Stateless
public class ProductServiceBean implements ProductService {

    @EJB
    private ProductDao productDao;

    @Override
    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> products = new ArrayList<>();
        products = productDao.findProductsByCategory(category);
        return products;
    }
}
