package edu.nju.onlineorder.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    private int pid;
    private String pname;
    private int stocknum;
    private double price;
    private String category;

    public Product(int pid, String pname, int stocknum, double price, String category) {
        this.pid = pid;
        this.pname = pname;
        this.stocknum = stocknum;
        this.price = price;
        this.category = category;
    }

    public Product(){

    }

    @Id
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getStocknum() {
        return stocknum;
    }

    public void setStocknum(int stocknum) {
        this.stocknum = stocknum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
