/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.xml.internal.fastinfoset.stax.events.ReadIterator;
import data.access.FriendDAO;
import data.access.RequestDAO;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 *
 * Class represents Account of user
 */
public class Account implements Serializable {
    private int accountId;
    private String avatar;
    private Date birthday;
    private ArrayList<String> dislikes;
    private String emailAddress;
    private String gender;
    private ArrayList<String> hobbies;
    private String interestGender;
    private String password;
    private String status;
    private String username;    
    
    private int numOfFriends;
    private int numOfRequests;
    private int numOfWaitings;
    
    private Career career;
    private Location location;
    
    public Account() {
        dislikes = new ArrayList<String>();
        hobbies = new ArrayList<String>();
        interestGender = "both";
        career = new Career();
        location = new Location();
        avatar = "http://farm8.staticflickr.com/7060/7061312585_b873307126.jpg";
    }
    
    /**
     * Computes age of user
     * @return age of user
     */
    public int getAge() {
        if (birthday == null) {
            return -1;
        }
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        int birthYear = Integer.parseInt(yearFormat.format(birthday));
        int currentYear = Integer.parseInt(yearFormat.format(new Date()));
        return currentYear - birthYear;
     
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public ArrayList<String> getDislikes() {
        return dislikes;
    }

    public void setDislikes(ArrayList<String> dislikes) {
        this.dislikes = dislikes;
    }

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String getInterestGender() {
        return interestGender;
    }

    public void setInterestGender(String interestGender) {
        this.interestGender = interestGender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumOfFriends() {
        return numOfFriends;
    }

    public void setNumOfFriends(int numOfFriends) {
        this.numOfFriends = numOfFriends;
    }

    public int getNumOfRequests() {
        return numOfRequests;
    }

    public void setNumOfRequests(int numOfRequests) {
        this.numOfRequests = numOfRequests;
    }

    public int getNumOfWaitings() {
        return numOfWaitings;
    }

    public void setNumOfWaitings(int numOfWaitings) {
        this.numOfWaitings = numOfWaitings;
    }

    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", birthday=" + birthday + ", emailAddress=" + emailAddress + ", gender=" + gender + ", password=" + password + ", username=" + username + '}';
    }    
    
    /**
     * Add display-search-info from ResultSet to account
     * display-search-info is: avatar, accountId, username, birthday, school, gender
     * 
     */
    public void setDisplayInfo(ResultSet resultSet) throws SQLException {
        setAvatar(resultSet.getString("avatar"));
        setAccountId(resultSet.getInt("accountId"));
        setUsername(resultSet.getString("username"));
        setBirthday(resultSet.getDate("birthday"));
        setGender(resultSet.getString("gender"));
    }
    
    /**
     * set basic info for account
     */
    public void setBasicInfo(ResultSet resultSet) throws SQLException {
        setAccountId(resultSet.getInt("accountId"));
        setAvatar(resultSet.getString("avatar"));
        setBirthday(resultSet.getDate("birthday"));
        setEmailAddress(resultSet.getString("emailAddress"));
        setGender(resultSet.getString("gender"));
        setInterestGender(resultSet.getString("interestGender"));
        setUsername(resultSet.getString("username"));
        setStatus(resultSet.getString("status"));
        
        setNumOfFriends(FriendDAO.getNumOfFriends(accountId));
        setNumOfRequests(RequestDAO.getNumOfRequests(accountId));
        setNumOfWaitings(RequestDAO.getNumOfWaitings(accountId));
    }
    
    /**
     * set hobby info for account
     */
    public void setHobbyInfo(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            hobbies.add(resultSet.getString("field"));
        }
    }
    
    /**
     * set dislike info for account
     */
    public void setDislikeInfo(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            dislikes.add(resultSet.getString("field"));
        }
    }
    
    /**
     * set career info for account
     */
    public void setCareerInfo(ResultSet resultSet) throws SQLException {
            career.setJob(resultSet.getString("job"));
            career.setSchool(resultSet.getString("school"));
    }
    
    /** 
     * set location info for account
     */
    public void setLocationInfo(ResultSet resultSet) throws SQLException {
        location.setAddress(resultSet.getString("address"));
        location.setCountry(resultSet.getString("country"));
        location.setHometown(resultSet.getString("hometown"));
    }
}
