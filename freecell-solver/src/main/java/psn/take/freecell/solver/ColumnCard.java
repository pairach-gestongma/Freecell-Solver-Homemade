/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PairachG
 */
public class ColumnCard {
    
    private ColumnNo column;
    private List<Card> cards;
    public ColumnCard(ColumnNo column){
        this.column = column;
        cards = new ArrayList();
    }

    public ColumnNo getColumn() {
        return column;
    }

    public void setColumn(ColumnNo column) {
        this.column = column;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
