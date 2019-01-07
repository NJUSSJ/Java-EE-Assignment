package edu.nju.weborder.dao;


import edu.nju.weborder.model.User;

import javax.ejb.Remote;

@Remote
public interface UserDao {

    public User find(int uid);

    public User find(String uname, String password);

    public void updateDeposit(int uid, double deposit);
}
