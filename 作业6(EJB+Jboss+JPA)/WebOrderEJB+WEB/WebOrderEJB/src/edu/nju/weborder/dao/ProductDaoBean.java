package edu.nju.weborder.dao;

import edu.nju.weborder.model.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
public class ProductDaoBean implements ProductDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Product find(int pid) {
        Product product = entityManager.find(Product.class, pid);
        return product;
    }

    @Override
    public ArrayList<Product> findProductsByCategory(String category) {
        ArrayList<Product> result = new ArrayList<>();
        List list = null;
        Query query = entityManager.createQuery("select new Product(p.pid, p.pname, p.stocknum, p.price, p.category) from Product p where p.category = :category");
        query.setParameter("category", category);
        list = query.getResultList();
        entityManager.clear();
        for (Object o: list
             ) {
            result.add((Product) o);
        }
        return result;
    }

    @Override
    public void updateStock(HashMap<String, String> list) {
        for (String pid: list.keySet()
             ) {
            Product product = entityManager.find(Product.class, Integer.parseInt(pid));
            product.setStocknum(product.getStocknum() - Integer.parseInt(list.get(pid)));
            entityManager.merge(product);
        }
    }

}
