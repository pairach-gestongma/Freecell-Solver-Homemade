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
public class CardsComparator implements Comparator<Cards>{

    @Override
    public int compare(Cards t, Cards t1) {
        return t.val() - t1.val();
    }
    
}
