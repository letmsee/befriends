/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

/**
 * Account for Matching Search
 */
public class AccountOfMatch extends Account {
    private int matchScore;
    private Account acc;
    
    public AccountOfMatch() {}
    
    public AccountOfMatch(Account acc, int matchScore) {
        this.acc = acc;
        this.matchScore = matchScore;
    }

    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }    
}
