package edu.nju.onlineorder.service.impl;

import edu.nju.onlineorder.dao.UserDao;
import edu.nju.onlineorder.dao.impl.UserDaoImpl;
import edu.nju.onlineorder.factory.DaoFactory;
import edu.nju.onlineorder.model.User;
import edu.nju.onlineorder.service.UserService;

public class UserServiceImpl implements UserService {

    private static UserDao userDao = DaoFactory.getUserDao();
    private static UserService userService = new UserServiceImpl();

    private UserServiceImpl(){

    }

    public static UserService getUserService(){
        if(userService == null){
            userService = new UserServiceImpl();
        }
        return userService;
    }
    @Override
    public Integer isValidUser(String uname, String password) {
        User user = userDao.find(uname, password);
        if(user == null){
            return null;
        }
        return user.getUid();
    }
}
