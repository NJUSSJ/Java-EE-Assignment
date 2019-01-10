package edu.nju.onlineorder.factory;

import edu.nju.onlineorder.dao.ProductDao;
import edu.nju.onlineorder.dao.UserDao;
import edu.nju.onlineorder.dao.impl.ProductDaoImpl;
import edu.nju.onlineorder.dao.impl.UserDaoImpl;

public class DaoFactory {
    public static UserDao getUserDao(){
        return UserDaoImpl.getUserDao();
    }
    public static ProductDao getProductDao(){
        return ProductDaoImpl.getProductDao();
    }
}
