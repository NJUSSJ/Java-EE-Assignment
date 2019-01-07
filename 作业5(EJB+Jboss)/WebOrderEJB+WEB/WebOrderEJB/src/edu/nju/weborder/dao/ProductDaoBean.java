package edu.nju.weborder.dao;

import edu.nju.weborder.model.Product;

import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Stateless
public class ProductDaoBean implements ProductDao{

    private static final DaoHelper daoHelper = DaoHelperImpl.getInstance();

    @Override
    public Product find(int pid) {
        Connection connection = daoHelper.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Product product = new Product();

        try {
            statement = connection.prepareStatement("select * from products where pid = ?");
            statement.setString(1, Integer.toString(pid));
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                product.setPid(pid);
                product.setCategory(resultSet.getString("cname"));
                product.setPname(resultSet.getString("pname"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStocknum(resultSet.getInt("stocknum"));
                return product;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            daoHelper.closeConnection(connection);
            daoHelper.closePreparedStatement(statement);
            daoHelper.closeResult(resultSet);
        }

        return null;
    }

    @Override
    public ArrayList<Product> findProductsByCategory(String cname) {
        Connection connection = daoHelper.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<Product> products = new ArrayList<>();

        try {
            statement = connection.prepareStatement("select * from products where cname = ?");
            statement.setString(1, cname);

            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Product product = new Product();
                product.setPid(resultSet.getInt("pid"));
                product.setStocknum(resultSet.getInt("stocknum"));
                product.setPrice(resultSet.getDouble("price"));
                product.setCategory(cname);
                product.setPname(resultSet.getString("pname"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            daoHelper.closeConnection(connection);
            daoHelper.closePreparedStatement(statement);
            daoHelper.closeResult(resultSet);
        }
        return products;
    }

    @Override
    public void updateStock(HashMap<String, String> list) {
        Connection connection = daoHelper.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("update products set stocknum = stocknum - ? where pid = ?");

            for (String pid: list.keySet()
            ) {
                statement.setString(1, list.get(pid));
                statement.setString(2, pid);
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            daoHelper.closeConnection(connection);
            daoHelper.closePreparedStatement(statement);
        }
    }

}
