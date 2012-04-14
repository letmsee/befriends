/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.access;

import business.Account;
import business.AccountOfMatch;
import business.MatchScoreComparator;
import business.MatchingResult;
import com.sun.corba.se.impl.orb.PrefixParserAction;
import data.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    
    /**
     * add an Account to database
     */
    public static void saveToDb(Account acc) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            // save basic info
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
            
          /*  preStatement.close();
            // save hobbies 
            for (String hobby : acc.getHobbies()) {
                sqlCode = 
                        "INSERT INTO Hobby " +
                        "(accountId, field) VAUES " +
                        "(?, ?) ";
                preStatement = connection.prepareStatement(sqlCode);
                preStatement.setInt(1, acc.getAccountId());
                preStatement.setString(2, hobby);
                preStatement.executeUpdate();
            }

            // save dislikes
            preStatement.close();
            for (String dislike : acc.getDislikes()) {
                sqlCode = 
                        "INSERT INTO Dislike " +
                        "(accountId, field) VAUES " +
                        "(?, ?) ";
                preStatement = connection.prepareStatement(sqlCode);
                preStatement.setInt(1, acc.getAccountId());
                preStatement.setString(2, dislike);
            } */

            
        /*    preStatement.close();
            // save career info
            sqlCode =
                    "INSERT INTO Career " +
                    "(school, job, accountId) VALUES " +
                    "(?, ?, ?) ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getCareer().getSchool());
            preStatement.setString(2, acc.getCareer().getSchool());
            preStatement.setInt(3, acc.getAccountId());
            preStatement.executeUpdate();
            
            preStatement.close();
            // save location info
            sqlCode = 
                    "INSERT INTO Location " +
                    "(address, country, hometown, accountId) VALUES " +
                    "(?, ?, ?) ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getLocation().getAddress());
            preStatement.setString(2, acc.getLocation().getCountry());
            preStatement.setString(3, acc.getLocation().getHometown());
            preStatement.setInt(4, acc.getAccountId());
            preStatement.executeUpdate(); */
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /**
     * update info for account
     */
    public static boolean updateAccount(Account acc) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            // update basic info
            String sqlCode = 
                    "UPDATE Account " +
                    "SET " +
                    "avatar = ?, " +
                    "birthday = ?, " +
                    "gender = ?, interestGender = ? " +
                    "WHERE accountId = ?";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setString(1, acc.getAvatar());
            // set birthday
            String birthdayStr = new SimpleDateFormat("yyyy/mm/dd").format(acc.getBirthday());
            preStatement.setString(2, birthdayStr);

            preStatement.setString(3, acc.getGender());
            preStatement.setString(4, acc.getInterestGender());
            
            preStatement.setInt(5, acc.getAccountId());
            preStatement.executeUpdate();

            // delete old hobbies
            preStatement.close();
            sqlCode = 
                    "DELETE FROM Hobby " +
                    "WHERE accountId = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, acc.getAccountId());
            preStatement.executeUpdate();
            preStatement.close();

            // update hobbies
            sqlCode = 
                    "INSERT INTO Hobby " +
                    "(accountId, field) VALUES " +
                    "(?, ?) ";
            for (String hobby : acc.getHobbies()) {
                preStatement = connection.prepareStatement(sqlCode);
                preStatement.setInt(1, acc.getAccountId());
                preStatement.setString(2, hobby);
                preStatement.executeUpdate();
                preStatement.close();
            }
            
            // delete old dislike
            sqlCode = 
                    "DELETE FROM Dislike " +
                    "WHERE accountId = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, acc.getAccountId());
            preStatement.executeUpdate();
            preStatement.close();

            // update hobbies
            sqlCode = 
                    "INSERT INTO Dislike " +
                    "(accountId, field) VALUES " +
                    "(?, ?) ";
            for (String dislike : acc.getDislikes()) {
                preStatement = connection.prepareStatement(sqlCode);
                preStatement.setInt(1, acc.getAccountId());
                preStatement.setString(2, dislike);
                preStatement.executeUpdate();
                preStatement.close();
            }
            
            // delete old career
            sqlCode =
                    "DELETE FROM Career " +
                    "WHERE accountId = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, acc.getAccountId());
            preStatement.executeUpdate();
            preStatement.close();
            
            // update new career
            sqlCode = 
                    "INSERT INTO Career " +
                    "(accountId, school, job) VALUES " +
                    "(?, ?, ?) ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, acc.getAccountId());
            preStatement.setString(2, acc.getCareer().getSchool());
            preStatement.setString(3, acc.getCareer().getJob());
            preStatement.executeUpdate();
            preStatement.close();
            
            // delete old location
            sqlCode = 
                    "DELETE FROM Location " +
                    "WHERE accountId = ? ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, acc.getAccountId());
            preStatement.executeUpdate();
            preStatement.close();
            
            // update new location
            sqlCode = 
                    "INSERT INTO Location " +
                    "(accountId, address, country, hometown) VALUES " +
                    "(?, ?, ?, ?) ";
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, acc.getAccountId());
            preStatement.setString(2, acc.getLocation().getAddress());
            preStatement.setString(3, acc.getLocation().getCountry());
            preStatement.setString(4, acc.getLocation().getHometown());
            preStatement.executeUpdate();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
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
    
    /**
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
    
    /**
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
     * @return AccountOfMatch object containing all personal info
     */
    public static AccountOfMatch getPersonalInfoMatch(int accountId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            AccountOfMatch acc = new AccountOfMatch();
            
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
     * using matching.
     * @param accountId - accountId of the user's account
     */
    public static ArrayList<AccountOfMatch> searchMatch(int accountId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            Account accForm = AccountDAO.getPersonalInfo(accountId);
            
            ArrayList<AccountOfMatch> searchResult = new ArrayList<AccountOfMatch>();
            String interestGender = accForm.getInterestGender();

            // get all user account into MatchResult
            String sqlCode;
            if (interestGender.equals("both")) {
                sqlCode = 
                        "SELECT * FROM Account " +
                        "WHERE accountId != ? ";
            } else {
                sqlCode =   
                        "SELECT * FROM Account " +
                        "WHERE accountId != ? " +
                        "AND gender = ? ";
            }
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            if (!interestGender.equals("both")) {
                preStatement.setString(2, accForm.getInterestGender());
            }

            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()) {
                AccountOfMatch accountOfMatch = (AccountOfMatch) getPersonalInfoMatch(resultSet.getInt("accountId"));
                searchResult.add(accountOfMatch);
            }
            
            // remove user's friends from the list
            for (int i = searchResult.size() - 1; i >= 0; i--) {
                Account acc = searchResult.get(i);
                if (FriendDAO.areFriends(acc.getAccountId(), accForm.getAccountId())) {
                    searchResult.remove(i);
                }
            }
            
            // give mark for each account in the list 
           giveMark(searchResult, accForm);
            return searchResult;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
    
    /**
     * give mark for search result
     */
    public static void giveMark(ArrayList<AccountOfMatch> searchResult, Account accForm) {
        // give mark for each account in the list 
            for (AccountOfMatch accountOfMatch : searchResult) {
                float matchScore = 0;
                // count number of common friends 
                accountOfMatch.setNumOfCommonFriends(
                        FriendDAO.getNumOfCommonFriends(accountOfMatch.getAccountId(), accForm.getAccountId()));
                matchScore = 10 * accountOfMatch.getNumOfCommonFriends();
                
                // compute score of likes
                for (String like : accountOfMatch.getHobbies()) {
                    for (String likeForm : accForm.getDislikes()) {
                        if (like.equals(likeForm)) {
                            matchScore += 3;
                        }
                    }
                }
                
                // compute score for interestGender
                String interestGenderForm = accForm.getInterestGender();
                String interest = accountOfMatch.getInterestGender();
                if (interestGenderForm.equals("both") ||
                        interestGenderForm.equals(accountOfMatch.getGender())) {
                    matchScore += 3.5;
                } else {
                    matchScore -= 3.5;
                }
                if (interest.equals("both") ||
                        interest.equals(accForm.getGender())) {
                    matchScore += 3.5;
                } else {
                    matchScore -= 3.5;
                }
                
                // compute score of denial
                int numOfDenials = DenialDAO.getNumOfDenials(accForm.getAccountId(), accountOfMatch.getAccountId());
                matchScore -= 5*numOfDenials;
                
                // compute score of dislikes
                for (String dislike : accountOfMatch.getDislikes()) {
                    for (String dislikeForm : accForm.getDislikes()) {
                        if (dislike.equals(dislikeForm)) {
                            matchScore += 2;
                        }
                    }
                }
                
                // compute score of confliction between dislikes and likes
                for (String dislike : accountOfMatch.getDislikes()) {
                    for (String likeForm : accForm.getHobbies()) {
                        if (dislike.equals(likeForm)) {
                            matchScore -= 1;
                        }
                    }
                }

                for (String like : accountOfMatch.getHobbies()) {
                    for (String dislike : accForm.getDislikes()) {
                        if (like.equals(dislike)) {
                            matchScore -= 1;
                        }
                    }
                }
                
                // compute score for difference of ages
                int ageDiff = Math.abs(accountOfMatch.getAge() - accForm.getAge());
                if (ageDiff <= 3) {
                    matchScore += 3;
                } else if (3 < ageDiff && ageDiff <= 5) {
                    matchScore += 2;
                } else if (5 < ageDiff && ageDiff <= 7) {
                    matchScore -= 0.5;
                } else {
                    matchScore -= 2;
                }
                
                
                // compute score of location
                String townForm = accForm.getLocation().getHometown();
                String town = accountOfMatch.getLocation().getHometown();
                if (townForm != null && town != null && townForm.equals(town)) {
                    matchScore += 3;
                }
               
                // save matchscore
                accountOfMatch.setMatchScore(matchScore);
            }
           
            // sort search result according the match score
            Collections.sort(searchResult,
                    Collections.reverseOrder(new MatchScoreComparator()) );
    }
    
    /**
     * rank new account by using matching
     * @param accountId - accountId of the account form
     */
    public static ArrayList<AccountOfMatch> rankNewAccount(int accountId) {
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preStatement = null;
        try {
            Account accForm = AccountDAO.getPersonalInfo(accountId);
            
            ArrayList<AccountOfMatch> searchResult = new ArrayList<AccountOfMatch>();
            String interestGender = accForm.getInterestGender();

            // get all user account into MatchResult
            String sqlCode;
            if (interestGender.equals("both")) {
                sqlCode = 
                        "SELECT acc.* " +
                        "FROM Account as acc, NewAccount as new " +
                        "WHERE new.accountId != ? AND " +
                        "      acc.accountId = new.accountId ";
            } else {
                sqlCode =   
                        "SELECT acc.* FROM Account " +
                        "WHERE new.accountId != ? AND " +
                        "      acc.accountId = new.accountId AND " +                       
                        "      acc.gender = ? ";
            }
            preStatement = connection.prepareStatement(sqlCode);
            preStatement.setInt(1, accountId);
            if (!interestGender.equals("both")) {
                preStatement.setString(2, accForm.getInterestGender());
            }

            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()) {
                AccountOfMatch accountOfMatch = (AccountOfMatch) getPersonalInfoMatch(resultSet.getInt("accountId"));
                searchResult.add(accountOfMatch);
            }

            giveMark(searchResult, accForm);
            return searchResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            freeDbResouce(preStatement, null, connection, pool);
        }
    }
}