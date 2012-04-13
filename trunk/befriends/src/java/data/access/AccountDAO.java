/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.access;

import business.Account;
import business.MatchingResult;
import com.sun.corba.se.impl.orb.PrefixParserAction;
import data.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.MatchResult;

/**
 *
 * Provides methods to access data of Account table
 */
public class AccountDAO extends DataDAO {
    /*
     * @return true if username and email address
     * of account is unique
     */
    public static boolean isUniquenessRight(Account acc, StringBuilder builder) {
        builder.setLength(0);
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            // check if username is unique
            String sqlCode =
                    "SELECT * FROM Account " +
                    "WHERE username = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getUsername());
            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                // means username not unique
                builder.append("Username already exists");
                return false;
            }
            
            // check if email address is unique
            sqlCode =
                    "SELECT * FROM Account " +
                    "WHERE emailAddress = ? ";
            preStatement.close();
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getEmailAddress());
            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                // means email address not unique
                builder.append("Email address alrealy exists");
                return false;
            }
            
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /*
     * add an Account to database
     */
    public static void saveToDb(Account acc) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode =
                    "INSERT INTO Account " +
                    "(username, password, emailAddress, gender, birthday, avatar, interestGender) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getUsername());
            preStatement.setString(2, acc.getPassword());
            preStatement.setString(3, acc.getEmailAddress());
            preStatement.setString(4, acc.getGender());

            // set birthday
            String birthdayStr = new SimpleDateFormat("yyyy/mm/dd").format(acc.getBirthday());
            preStatement.setString(5, birthdayStr);
            preStatement.setString(6, acc.getAvatar());
            preStatement.setString(7, acc.getInterestGender());
            preStatement.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /*
     * @return true if login info is valid
     */
    public static boolean loginInfoIsValid(Account acc) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode =
                    "SELECT * FROM Account " +
                    "WHERE username = ? AND password = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getUsername());
            preStatement.setString(2, acc.getPassword());
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
    
     /*
     * fills all basic information retrieved from database for account
     * basic information: accountId, username, emailAddress
     * @return Account that contain all information retrieve from database
     */
    public static Account getAccount(Account acc) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "SELECT * FROM Account " +
                    "WHERE username = ? AND password = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getUsername());
            preStatement.setString(2, acc.getPassword());
            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                acc.setBasicInfo(resultSet);
                return acc;
            }
            else {
                return null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
  
      /*
     * fills all basic information retrieved from database for account
     * basic information: accountId, username, emailAddress
     * @return Account that contain all information retrieve from database
     */
    public static Account getAccount(int accountId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "SELECT * FROM Account " +
                    "WHERE accountId = ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                Account acc = new Account();
                acc.setBasicInfo(resultSet);
                return acc;
            }
            else {
                return null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
     /*
     * Use usename to search account that has similar usernames in the system
     * @return the list of accounts as the search result
     */
    public static ArrayList<Account> searchByUsername(String username) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "SELECT * FROM Account " +
                    "WHERE username LIKE ? " +
                    "ORDER BY (username) ASC";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, username + "%");
            ResultSet resultSet = preStatement.executeQuery();

            ArrayList<Account> list = new ArrayList<Account>();
            while (resultSet.next()) {
                Account acc = new Account();
                acc.setBasicInfo(resultSet);
                list.add(acc);
            }
            return list;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
     /*
     * Use usename to search account that has similar usernames in the system
     * @param accountId - accountId of the account
     * @param limitOfResults - the maximum number of results
     * @return the list of accounts as the search result
     */
    public static ArrayList<Account> searchByUsername(String username, int limitOfResults) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            String sqlCode = 
                    "SELECT * FROM Account " +
                    "WHERE username LIKE ? " +
                    "ORDER BY (username) ASC" +
                    "LIMIT ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, username + "%");
            preStatement.setInt(2, limitOfResults);
            ResultSet resultSet = preStatement.executeQuery();

            ArrayList<Account> list = new ArrayList<Account>();
            while (resultSet.next()) {
                Account acc = new Account();
                acc.setBasicInfo(resultSet);
                list.add(acc);
            }
            return list;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /**
     * Retrieve personal info from database
     * @param accountId - id of person to get info
     * @return Account object containing all personal info
     */
    public static Account getPersonalInfo(int accountId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            Account acc = new Account();
            
            // get basis information
            String sqlCode = 
                    "SELECT * FROM Account " +
                    "WHERE accountId = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                acc.setBasicInfo(resultSet);
            }
            
            resultSet.close();
            preStatement.close();
            // get hobby info
            sqlCode = 
                    "SELECT * FROM Hobby " +
                    "WHERE accountId = ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            resultSet = preStatement.executeQuery();
            acc.setHobbyInfo(resultSet);
            
            resultSet.close();
            preStatement.close();
            // get dislike info
            sqlCode = 
                    "SELECT * FROM Dislike " +
                    "WHERE accountId = ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            resultSet = preStatement.executeQuery();
            acc.setDislikeInfo(resultSet);
            
           resultSet.close();
            preStatement.close();
            // get career info
            sqlCode = 
                    "SELECT * FROM Career " +
                    "WHERE accountId = ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                acc.setCareerInfo(resultSet);
            }
            
            resultSet.close();
            preStatement.close();
            // get location info
            sqlCode = 
                    "SELECT * FROM Location " +
                    "WHERE accountId = ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                acc.setLocationInfo(resultSet);
            } 

            return acc;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /**
     * Search users that appropriate to the given User by 
     * using matching algorithm
     */
//   public static MatchingResult searchMatch(int accountId) {
//       ConnectionPool pool = ConnectionPool.getInstance();
//       Connection connection = pool.getConnection();
//       PreparedStatement preStatement = null;
//       try {
//           Account accForm = AccountDAO.getAccount(accountId);
//           
//           MatchingResult matchingResult = new MatchingResult();
//           
//           // get all user account into MatchResult
//           String sqlCode = 
//                   "SELECT * FROM Account";
//       }
//   }
}