/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.Comparator;

/**
 *
 * @author pairach.g
 */
public class CardComparator implements Comparator<Card>{

    @Override
    public int compare(Card t, Card t1) {
        return t.val() - t1.val();
    }
    
}
