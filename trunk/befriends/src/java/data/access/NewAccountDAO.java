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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author duongna
 */
public class NewAccountDAO extends DataDAO {
    public static boolean saveNewAccount(int accountId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            Date date = new Date();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy/mm/dd");
            String sqlCode = 
                    "INSERT INTO NewAccount " +
                    "(accountId, date) VALUES " +
                    "(?, ?) ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            preStatement.setDate(2, new java.sql.Date(date.getTime()));
            preStatement.executeUpdate();
            preStatement.close();
            
            // delete old account
            sqlCode =
                    "DELETE FROM NewAccount " +
                    "WHERE (SELECT DATEDIFF(?, date) > 3)";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setDate(1, new java.sql.Date((new Date()).getTime()) );
            preStatement.executeUpdate();
            preStatement.close();

            return true;            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
}
