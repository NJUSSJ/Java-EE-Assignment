package edu.nju.onlineorder.dao.impl;

import edu.nju.onlineorder.Util.HibernateUtil;
import edu.nju.onlineorder.dao.ProductDao;
import edu.nju.onlineorder.model.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

    private static ProductDao productDao = new ProductDaoImpl();

    private ProductDaoImpl(){

    }

    public static ProductDao getProductDao(){
        return productDao;
    }


    @Override
    public Product find(int pid) {
        return (Product) super.find(Product.class, pid);
    }

    @Override
    public ArrayList<Product> findProductsByCategory(String cname) {
        ArrayList<Product> result = new ArrayList<>();
        try{
            Session session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Product.class);
            criteria.add(Restrictions.eq("category", cname));
            List list = criteria.list();
            transaction.commit();
            for (Object o: list
                 ) {
                result.add((Product) o);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateStock(HashMap<String, String> list) {
        for (String pid: list.keySet()
             ) {
            Product product = (Product) super.find(Product.class, Integer.parseInt(pid));
            product.setStocknum(product.getStocknum() - Integer.parseInt(list.get(pid)));
            super.update(product);
        }
    }
}
