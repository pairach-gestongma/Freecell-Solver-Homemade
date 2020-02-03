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
    private ColumnCard moveToCol;
    private Card moveCard;

    public ColumnAndCard(ColumnCard column, Card card) {
        this.column = column;
        this.card = card;
    }

    public ColumnAndCard(ColumnCard column, Card card, ColumnCard moveToCol, Card moveCard) {
        this.column = column;
        this.card = card;
        this.moveToCol = moveToCol;
        this.moveCard = moveCard;
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

    public ColumnCard getMoveToCol() {
        return moveToCol;
    }

    public void setMoveToCol(ColumnCard moveToCol) {
        this.moveToCol = moveToCol;
    }

    public Card getMoveCard() {
        return moveCard;
    }

    public void setMoveCard(Card moveCard) {
        this.moveCard = moveCard;
    }

}
