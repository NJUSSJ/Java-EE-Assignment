package edu.nju.weborder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DaoHelper {

    public Connection getConnection();

    public void closeConnection(Connection connection);

    public void closePreparedStatement(PreparedStatement statement);

    public void closeResult(ResultSet resultSet);

}
