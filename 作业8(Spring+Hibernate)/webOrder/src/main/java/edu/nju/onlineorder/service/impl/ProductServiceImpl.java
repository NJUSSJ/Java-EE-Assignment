package edu.nju.onlineorder.service.impl;

import edu.nju.onlineorder.dao.ProductDao;
import edu.nju.onlineorder.model.Product;
import edu.nju.onlineorder.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service(value = "ProductService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> products = new ArrayList<>();
        products = productDao.findProductsByCategory(category);
        return products;
    }
}
