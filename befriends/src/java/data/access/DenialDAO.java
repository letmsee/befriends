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
 * Class provides methods for changing denial table
 */
public class DenialDAO extends DataDAO {
    
    /**
     * check if denial exists
     * @param denierId - accountId of denying person
     * @param requestId - accountId of request person
     * @return true if exist
     */
    public static boolean denialExists(int denierId, int requestId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode =
                    "SELECT * FROM Denial " +
                    "WHERE denierId = ? AND requestId = ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, denierId);
            preStatement.setInt(2, requestId);
            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
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
    
    /**
     * add an denial into denial table
     * @param denierId - accountId of denying person
     * @param requestId - accountId of request person
     * @return true if success 
     */
    public static boolean addDenial(int denialId, int requestId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode =
                    "INSERT INTO Denial VALUES" +
                    "(?, ?)";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, denialId);
            preStatement.setInt(2, requestId);
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
