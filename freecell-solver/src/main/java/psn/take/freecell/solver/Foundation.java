/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author pairach.g
 */
public class Foundation {
    private CardType cardType;
    private Stack<Card> cards;
    private List<Card> expectCards;
    public Foundation(CardType cardType){
        this.cardType = cardType;
        cards = new Stack();
        expectCards = new ArrayList();
        for(int i=0;i<13;i++){
            if(i+1 == 1){
                expectCards.add(Card.valueOf("_A" + cardType.name()));
                continue;
            }else if(i+1 == 10){
                expectCards.add(Card.valueOf("_T" + cardType.name()));
                continue;
            }else if(i+1 == 11){
                expectCards.add(Card.valueOf("_J" + cardType.name()));
                continue;
            }else if(i+1 == 12){
                expectCards.add(Card.valueOf("_Q" + cardType.name()));
                continue;
            }else if(i+1 == 13){
                expectCards.add(Card.valueOf("_K" + cardType.name()));
                continue;
            }
            expectCards.add(Card.valueOf("_" + (i+1) + cardType.name()));
        }
//        for(Card c : expectCards){
//            System.out.println(c.name());
//        }
    }
    
    public void initWithCard(Card card){
        cards.clear();
        for(int i=0;i<card.val();i++){
            if(i+1 == 1){
                cards.add(Card.valueOf("_A" + cardType.name()));
                continue;
            }else if(i+1 == 10){
                cards.add(Card.valueOf("_T" + cardType.name()));
                continue;
            }else if(i+1 == 11){
                cards.add(Card.valueOf("_J" + cardType.name()));
                continue;
            }else if(i+1 == 12){
                cards.add(Card.valueOf("_Q" + cardType.name()));
                continue;
            }else if(i+1 == 13){
                cards.add(Card.valueOf("_K" + cardType.name()));
                continue;
            }
            cards.add(Card.valueOf("_" + (i+1) + cardType.name()));
        }
    }
    
    public Card nextNeedCard(){
        return expectCards.get(cards.size());
    }
    
    public CardType cardType(){
        return cardType;
    }
    
    public void addCard(Card card, ColumnCard srcColumn){
        cards.add(card);
        srcColumn.getCards().remove(card);
    }
    
    public Card currentCard(){
        return cards.lastElement();
    }
}
