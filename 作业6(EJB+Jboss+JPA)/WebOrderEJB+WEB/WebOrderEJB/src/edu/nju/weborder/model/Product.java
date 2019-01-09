package edu.nju.weborder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {
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

    @Column(nullable = false)
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Column(nullable = false)
    public int getStocknum() {
        return stocknum;
    }

    public void setStocknum(int stocknum) {
        this.stocknum = stocknum;
    }

    @Column(nullable = false)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
