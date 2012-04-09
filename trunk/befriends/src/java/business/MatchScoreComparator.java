/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.util.Comparator;

/**
 *
 * @author duongna
 */
public class MatchScoreComparator implements Comparator {
    public int compare(Object obj1, Object obj2) {
        int score1 = ((AccountOfMatch) obj1).getMatchScore();
        int score2 = ((AccountOfMatch) obj2).getMatchScore();
        if (score1 > score2) {
            return 1;
        } else if (score1 == score2) {
            return 0;
        } else {
            return -1;
        }
    }
}
