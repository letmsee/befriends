/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.io.Serializable;

/**
 * Class contains information about career of user
 */
public class Career implements Serializable {
    private String job;
    private String school;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }    
}
