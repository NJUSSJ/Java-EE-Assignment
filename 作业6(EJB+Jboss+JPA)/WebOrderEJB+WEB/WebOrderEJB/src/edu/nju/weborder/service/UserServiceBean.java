package edu.nju.weborder.service;

import edu.nju.weborder.dao.UserDao;
import edu.nju.weborder.model.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserServiceBean implements UserService {

    @EJB
    private UserDao userDao;

    @Override
    public Integer isValidUser(String uname, String password) {
        User user = userDao.find(uname, password);
        if(user == null){
            return null;
        }
        return user.getUid();
    }
}
