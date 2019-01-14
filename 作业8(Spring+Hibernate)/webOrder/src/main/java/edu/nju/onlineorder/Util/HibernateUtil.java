package edu.nju.onlineorder.Util;

import edu.nju.onlineorder.model.Product;
import edu.nju.onlineorder.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        try{
            Configuration configuration;
            ServiceRegistry serviceRegistry;
            configuration = new Configuration().configure();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Product.class);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Session getSession(){
        return getSessionFactory().getCurrentSession();
    }
}
