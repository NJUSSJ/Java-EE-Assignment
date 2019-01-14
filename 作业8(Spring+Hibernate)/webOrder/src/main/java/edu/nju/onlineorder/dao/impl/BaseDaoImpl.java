package edu.nju.onlineorder.dao.impl;

import edu.nju.onlineorder.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository(value = "BaseDao")
public class BaseDaoImpl implements BaseDao {

    @Autowired
    protected SessionFactory sessionFactory;

    public Session getSeesion(){
        return sessionFactory.getCurrentSession();
    }

    public Session getNewSession(){
        return sessionFactory.openSession();
    }

    @Override
    public void flush() {
        getSeesion().flush();
    }

    @Override
    public void clear() {
        getSeesion().clear();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object find(Class c, Integer id) {
        try{
            Session session = getNewSession();
            Transaction transaction = session.beginTransaction();
            Object o = session.get(c, id);
            transaction.commit();
            return o;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List getAllList(Class c) {
        return null;
    }

    @Override
    public Long getTotalCount(Class c) {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void save(Object bean) {
        try {
            Session session = getSeesion();
            Transaction transaction = session.beginTransaction();
            session.merge(bean);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void update(Object bean) {
        try{
            Session session = getNewSession();
            Transaction transaction = session.beginTransaction();
            session.update(bean);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void delete(Object bean) {
        try {
            Session session = getNewSession() ;
            Transaction tx=session.beginTransaction();
            session.delete(bean);;
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Class c, String id) {

    }

    @Override
    public void delete(Class c, String[] ids) {

    }
}
