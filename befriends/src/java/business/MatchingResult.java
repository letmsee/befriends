/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class represents result of matching search
 */
public class MatchingResult {
    ArrayList<AccountOfMatch> list;
    
    public MatchingResult() {
        list = new ArrayList<AccountOfMatch>();
    }
    
    public void add(AccountOfMatch accOfMatch) {
        list.add(accOfMatch);
    }
    
    public AccountOfMatch get(int index) {
        return list.get(index);
    }

    public ArrayList<AccountOfMatch> getList() {
        return list;
    }

    public void setList(ArrayList<AccountOfMatch> list) {
        this.list = list;
    }
    
    public void sort() {
        Collections.sort(list, new MatchScoreComparator());
    }
}
