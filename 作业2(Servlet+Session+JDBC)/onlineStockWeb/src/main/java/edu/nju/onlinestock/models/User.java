package edu.nju.onlinestock.models;

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

    public String getUname() {
        return uname;
    }

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
}
