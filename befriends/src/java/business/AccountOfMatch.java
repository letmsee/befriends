/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

/**
 * Account for Matching Search
 */
public class AccountOfMatch extends Account {
    private float matchScore;
    private int numOfCommonFriends;
    
    public float getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(float matchScore) {
        this.matchScore = matchScore;
    }
      
    public int getNumOfCommonFriends() {
        return numOfCommonFriends;
    }

    public void setNumOfCommonFriends(int numOfCommonFriends) {
        this.numOfCommonFriends = numOfCommonFriends;
    }
}
