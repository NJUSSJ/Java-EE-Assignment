package edu.nju.onlineorder.service.impl;

import edu.nju.onlineorder.dao.ProductDao;
import edu.nju.onlineorder.factory.DaoFactory;
import edu.nju.onlineorder.model.Product;
import edu.nju.onlineorder.service.ProductService;

import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {

    private static ProductDao productDao = DaoFactory.getProductDao();
    private static ProductService productService = new ProductServiceImpl();

    private ProductServiceImpl(){

    }

    public static ProductService getProductService(){
        if(productService == null){
            productService = new ProductServiceImpl();
        }
        return productService;
    }
    @Override
    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> products = new ArrayList<>();
        products = productDao.findProductsByCategory(category);
        return products;
    }
}
