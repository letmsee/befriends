/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 *
 * Class represents Account of user
 */
public class Account implements Serializable {
    private int accountId;
    private Date birthday;
    private String emailAddress;
    private String gender;
    private String password;
    private String username;    
    private String school;

    /**
     * Computes age of user
     * @return age of user
     */
    public int getAge() {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        int birthYear = Integer.parseInt(yearFormat.format(birthday));
        int currentYear = Integer.parseInt(yearFormat.format(new Date()));
        return currentYear - birthYear;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", birthday=" + birthday + ", emailAddress=" + emailAddress + ", gender=" + gender + ", password=" + password + ", username=" + username + '}';
    }    
}
