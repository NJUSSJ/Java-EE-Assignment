package edu.nju.weborder.service;

import javax.ejb.Remote;

@Remote
public interface UserService {

    public Integer isValidUser(String uname, String password);

}
