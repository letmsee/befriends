/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.access;

import data.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class provides methods about request list table
 */
public class RequestDAO extends DataDAO {

    /**
     * @return true if account in the request list
     */
    public static boolean accountInRequest(int targetId1, int requestId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connect = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "SELECT * FROM Request " +
                    "WHERER targetId = ? AND requestId = ?";
            preStatement = connect.prepareStatement(sqlCode);
            preStatement.setInt(1, targetId1);
            preStatement.setInt(2, requestId);
            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            freeDbResouce(preStatement, null, connect, pool);
        }
    }
    
    /**
     * add accountId into target User's request list
     */
    public static boolean addAccount(int targetId, int requestId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "INSERT INTO Request VALUES " +
                    "(?, ?)";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, targetId);
            preStatement.setInt(2, requestId);
            int nRows = preStatement.executeUpdate();
            if (nRows > 0) {
                return true;
            }
            else {
                return false;
            }            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
}
