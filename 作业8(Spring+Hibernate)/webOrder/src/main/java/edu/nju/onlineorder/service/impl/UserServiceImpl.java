package edu.nju.onlineorder.service.impl;

import edu.nju.onlineorder.dao.UserDao;
import edu.nju.onlineorder.model.User;
import edu.nju.onlineorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

    @Autowired
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
