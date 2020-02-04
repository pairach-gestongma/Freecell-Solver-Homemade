/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.List;

/**
 *
 * @author PairachG
 */
public class ColumnAndCard {
    
    private ColumnCard column;
    private Card card;
    private List<Card> cards;
    private ColumnCard moveToCol;
    private Card moveToCard;

    public ColumnAndCard(ColumnCard column, Card card) {
        this.column = column;
        this.card = card;
    }

    public ColumnAndCard(ColumnCard column, Card card, ColumnCard moveToCol, Card moveToCard) {
        this.column = column;
        this.card = card;
        this.moveToCol = moveToCol;
        this.moveToCard = moveToCard;
    }
    
    public ColumnAndCard(ColumnCard column, List<Card> cards, ColumnCard moveToCol, Card moveToCard) {
        this.column = column;
        this.cards = cards;
        this.moveToCol = moveToCol;
        this.moveToCard = moveToCard;
    }

    public List<Card> getCards() {
        return cards;
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

    public Card getMoveToCard() {
        return moveToCard;
    }

    public void setMoveToCard(Card moveToCard) {
        this.moveToCard = moveToCard;
    }

}
