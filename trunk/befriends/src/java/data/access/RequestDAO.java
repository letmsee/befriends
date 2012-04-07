/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.access;

import business.Account;
import data.pool.ConnectionPool;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
                    "WHERE targetId = ? AND requestId = ?";
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
    
    /**
     * to get request list of User
     * @param accountId - accountId of User 
     * @return request list of User
     */
    public static ArrayList<Account> getRequestList(int accountId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            // get list of accountId
            String sqlCode = 
                    "SELECT acc.* " +
                    "FROM Request as req, Account as acc " +
                    "WHERE req.targetId = ? AND " +
                    "      acc.accountId = req.requestId";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            ResultSet resultSet = preStatement.executeQuery();

            ArrayList<Account> requestList = new ArrayList<Account>();
            while (resultSet.next()) {
                Account acc = new Account();
                acc.setAccountId(resultSet.getInt("accountId"));
                acc.setUsername(resultSet.getString("username"));
                acc.setEmailAddress(resultSet.getString("emailAddress"));
                acc.setBirthday(resultSet.getDate("birthday"));
                requestList.add(acc);
            } 
            return requestList;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
}
    

