/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

/**
 *
 * @author PairachG
 */
public class ColumnAndCard {
    
    private ColumnCard column;
    private Card card;

    public ColumnAndCard(ColumnCard column, Card card) {
        this.column = column;
        this.card = card;
    }

    public ColumnCard getColumn() {
        return column;
    }

    public void setColumn(ColumnCard column) {
        this.column = column;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
