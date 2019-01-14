package edu.nju.onlineorder.dao;

import edu.nju.onlineorder.model.User;

public interface UserDao extends BaseDao{
    public User find(String uname, String password);
    public void updateDeposit(int uid, double deposit);
    public User find(int uid);
}
