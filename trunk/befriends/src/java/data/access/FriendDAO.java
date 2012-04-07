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
}
