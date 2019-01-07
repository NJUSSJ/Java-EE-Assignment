package edu.nju.weborder.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

public class DaoHelperImpl implements DaoHelper{
    private static DaoHelperImpl baseDao = new DaoHelperImpl();

    private Connection connection = null;
    private DataSource dataSource = null;
    private InitialContext jndiContext = null;


    private DaoHelperImpl(){

    }

    public static DaoHelperImpl getInstance(){
        if(baseDao == null){
            baseDao = new DaoHelperImpl();
        }
        return baseDao;
    }
    @Override
    public Connection getConnection() {
        try{
            final Hashtable properties = new Hashtable();
            properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

            try {
                jndiContext = new InitialContext(properties);
                dataSource = (DataSource) jndiContext.lookup("java:/MySqlDS");
                if(dataSource == null) System.out.println("datasource null");
            }catch (NamingException e){
                e.printStackTrace();
            }
            connection = dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closePreparedStatement(PreparedStatement statement) {
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeResult(ResultSet resultSet) {
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
