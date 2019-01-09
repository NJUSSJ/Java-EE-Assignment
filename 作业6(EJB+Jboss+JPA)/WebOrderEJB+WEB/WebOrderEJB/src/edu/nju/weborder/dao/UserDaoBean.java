package edu.nju.weborder.dao;

import edu.nju.weborder.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UserDaoBean implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public User find(int uid) {
        User user = entityManager.find(User.class, uid);
        return user;
    }

    @Override
    public User find(String uname, String password) {
        List list = null;
        try{
            Query query = entityManager.createQuery("select new User(u.uid, u.uname, u.password, u.deposit) from User u where u.uname=:uname and u.password=:password");
            query.setParameter("uname", uname);
            query.setParameter("password", password);
            list = query.getResultList();
            entityManager.clear();
            if(list.size() == 0){
                return null;
            }else{
                return (User) list.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateDeposit(int uid, double deposit) {
        User user = entityManager.find(User.class, uid);
        user.setDeposit(deposit);
        entityManager.merge(user);
    }
}
