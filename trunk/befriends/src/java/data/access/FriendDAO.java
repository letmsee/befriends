/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.access;

import business.Account;
import data.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class provides methods about friendship of users 
 */
public class FriendDAO extends DataDAO {

    /**
     * @param accountId1 - accountId of user 1
     * @param accountId2 - accountId of user 2
     * @return true if two account are friend
     */
    public static boolean areFriends(int accountId1, int accountId2) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode =
                    "SELECT * FROM Friend " +
                    "WHERE (accountId1 = ? AND accountId2 = ?) OR " +
                    "      (accountId1 = ? AND accountId2 = ?) ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId1);
            preStatement.setInt(2, accountId2);
            preStatement.setInt(3, accountId2);
            preStatement.setInt(4, accountId1);
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
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /**
     * add friend relation into table Friend
     * @param accountId1 - account of first user
     * @param accountId2 - account of second user
     * @return true if success
     */
    public static boolean addFriends(int accountId1, int accountId2) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "INSERT INTO Friend VALUES " +
                    "(?, ?)";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId1);
            preStatement.setInt(2, accountId2);
            int nRows = preStatement.executeUpdate();
            if (nRows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException sqle)  {
            sqle.printStackTrace();
            return false;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /**
     * @return list of account of User's friends
     */
    public static ArrayList<Account> getFriendList(int accountId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode =
                    "SELECT acc.* " +
                    "FROM Account as acc, Friend as fr " +
                    "WHERE (fr.accountId1 = ? AND acc.accountId = fr.accountId2) OR " +
                    "      (fr.accountId2 = ? AND acc.accountId = fr.accountId1)";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            preStatement.setInt(2, accountId);
            ResultSet resutSet = preStatement.executeQuery();
            
            ArrayList<Account> friendList = new ArrayList<Account>();
            while (resutSet.next()) {
                Account acc = new Account();
                acc.setBasicInfo(resutSet);
                friendList.add(acc);
            }
            return friendList;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /**
     * delete a friend relationship
     * @param accountId1 - accountId of first person
     * @param accountId2 - accountId of second person
     * @return true if success
     */
    public static boolean deleteFriend(int accountId1, int accountId2) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "DELETE FROM Friend " +
                    "WHERE (accountId1 = ? AND accountId2 = ?) OR " +
                    "      (accountId1 = ? AND accountId2 = ?)";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId1);
            preStatement.setInt(2, accountId2);
            preStatement.setInt(3, accountId2);
            preStatement.setInt(4, accountId1);
            int nRows = preStatement.executeUpdate();
            if (nRows > 0) {
                return true;
            } else {
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
