/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.io.Serializable;

/**
 * Class contains information about location of user
 * @author duongna
 */
public class Location implements Serializable {
    private String address;
    private String country;
    private String hometown;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
}
