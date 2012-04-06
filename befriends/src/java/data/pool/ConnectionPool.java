/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.pool;

import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Class for Database pool, provides connection from
 * each thread to database
 */
public class ConnectionPool {
    private static ConnectionPool pool;
    private static DataSource dataSource;
    
    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/duongna");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }
    
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }
    
    public void freeConnection(Connection connection) {
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
    }
}
