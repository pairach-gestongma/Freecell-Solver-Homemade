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
        if(cardType.equals(CardType.C)){
            for(int i=0;i<13;i++){
                if(i+1 == 1){
                    expectCards.add(Card.valueOf("_AC"));
                    continue;
                }else if(i+1 == 10){
                    expectCards.add(Card.valueOf("_TC"));
                    continue;
                }else if(i+1 == 11){
                    expectCards.add(Card.valueOf("_JC"));
                    continue;
                }else if(i+1 == 12){
                    expectCards.add(Card.valueOf("_QC"));
                    continue;
                }else if(i+1 == 13){
                    expectCards.add(Card.valueOf("_KC"));
                    continue;
                }
                expectCards.add(Card.valueOf("_" + (i+1) + "C"));
            }
        }
        for(Card c : expectCards){
            System.out.println(c.name());
        }
    }
    
}
