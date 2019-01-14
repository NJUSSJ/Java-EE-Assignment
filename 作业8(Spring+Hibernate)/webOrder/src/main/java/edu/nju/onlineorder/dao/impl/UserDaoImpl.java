package edu.nju.onlineorder.dao.impl;

import edu.nju.onlineorder.Util.HibernateUtil;
import edu.nju.onlineorder.dao.UserDao;
import edu.nju.onlineorder.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "UserDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    @Override
    public User find(String uname, String password) {
        try{
            Session session = getNewSession();
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("uname", uname));
            criteria.add(Restrictions.eq("password", password));

            List list = criteria.list();

            transaction.commit();
            if(list.size() == 0) return null;
            return (User) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateDeposit(int uid, double deposit) {
        User user = (User) super.find(User.class, uid);
        user.setDeposit(deposit);
        super.update(user);
    }

    @Override
    public User find(int uid){
        return (User) super.find(User.class, uid);
    }
}
