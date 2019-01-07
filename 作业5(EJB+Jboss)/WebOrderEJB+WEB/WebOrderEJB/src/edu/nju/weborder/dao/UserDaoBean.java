package edu.nju.weborder.dao;

import edu.nju.weborder.model.User;

import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateless
public class UserDaoBean implements UserDao {

    private static final DaoHelper daoHelper = DaoHelperImpl.getInstance();

    @Override
    public User find(int uid) {
        Connection connection = daoHelper.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();

        try {
            statement = connection.prepareStatement("select * from users where uid = ?");
            statement.setString(1, Integer.toString(uid));
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                user.setUid(uid);
                user.setUname(resultSet.getString("uname"));
                user.setPassword(resultSet.getString("password"));
                user.setDeposit(resultSet.getDouble("deposit"));
                return user;
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
    public User find(String uname, String password) {
        Connection connection = daoHelper.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("select * from users where uname = ? and password = ?");
            statement.setString(1, uname);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            User user = new User();

            while(resultSet.next()){
                user.setUid(resultSet.getInt("uid"));
                user.setUname(resultSet.getString("uname"));
                user.setPassword(resultSet.getString("password"));
                user.setDeposit(resultSet.getDouble("deposit"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            daoHelper.closeConnection(connection);
            daoHelper.closePreparedStatement(statement);
            daoHelper.closeResult(resultSet);
        }
        return null;
    }

    @Override
    public void updateDeposit(int uid, double deposit) {
        Connection connection = daoHelper.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("update users set deposit = ? where uid = ?");
            statement.setString(1, Double.toString(deposit));
            statement.setString(2, Integer.toString(uid));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            daoHelper.closeConnection(connection);
            daoHelper.closePreparedStatement(statement);
        }
    }
}
