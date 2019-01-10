package edu.nju.onlineorder.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    private String uname;
    private int uid;
    private String password;
    private double deposit;

    public User(int uid, String uname, String password, double deposit){
        this.password = password;
        this.uname = uname;
        this.uid = uid;
        this.deposit = deposit;
    }

    public User(){

    }

    public String getUname() {
        return uname;
    }

    @Id
    public int getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
