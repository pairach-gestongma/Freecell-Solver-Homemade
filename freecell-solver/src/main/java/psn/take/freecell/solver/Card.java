/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * C black round tree 
 * D red square
 * S black angle tree 
 * H red heart
 * @author pairach.g 
 */
public  enum Card {
    _AC(1,CardType.C), _2C(2,CardType.C), _3C(3,CardType.C), _4C(4,CardType.C), _5C(5,CardType.C), _6C(6,CardType.C), _7C(7,CardType.C), _8C(8,CardType.C), _9C(9,CardType.C), _TC(10,CardType.C), _JC(11,CardType.C), _QC(12,CardType.C), _KC(13,CardType.C),
    _AD(1,CardType.D), _2D(2,CardType.D), _3D(3,CardType.D), _4D(4,CardType.D), _5D(5,CardType.D), _6D(6,CardType.D), _7D(7,CardType.D), _8D(8,CardType.D), _9D(9,CardType.D), _TD(10,CardType.D), _JD(11,CardType.D), _QD(12,CardType.D), _KD(13,CardType.D),
    _AS(1,CardType.S), _2S(2,CardType.S), _3S(3,CardType.S), _4S(4,CardType.S), _5S(5,CardType.S), _6S(6,CardType.S), _7S(7,CardType.S), _8S(8,CardType.S), _9S(9,CardType.S), _TS(10,CardType.S), _JS(11,CardType.S), _QS(12,CardType.S), _KS(13,CardType.S),
    _AH(1,CardType.H), _2H(2,CardType.H), _3H(3,CardType.H), _4H(4,CardType.H), _5H(5,CardType.H), _6H(6,CardType.H), _7H(7,CardType.H), _8H(8,CardType.H), _9H(9,CardType.H), _TH(10,CardType.H), _JH(11,CardType.H), _QH(12,CardType.H), _KH(13,CardType.H);
    
    public static final String cardNos = "A23456789TJQK";
    private int val;
    private CardType type;
    private List<Card> possibleCardsPrevInColumn;
    private List<Card> possibleCardsNextInColumn;
    
    Card(int val, CardType type){
        this.val = val;
        this.type = type;
        
        possibleCardsPrevInColumn = new ArrayList();
        possibleCardsNextInColumn = new ArrayList();
    }
    
    public void initialPrevNext(){
        List<CardType> prevOrNextCardTypes = new ArrayList();
        if(type.equals(CardType.C) || type.equals(CardType.S)){
            prevOrNextCardTypes.add(CardType.D);
            prevOrNextCardTypes.add(CardType.H);
        }else{
            prevOrNextCardTypes.add(CardType.C);
            prevOrNextCardTypes.add(CardType.S);
        }
        
        
        int prevVal = val + 1;
        int nextVal = val - 1;
        if(prevVal - 1 >= 0 && prevVal - 1 < 13){
            for(CardType ct : prevOrNextCardTypes){
                try{
//                    System.out.println(">>_" + cardNos.charAt(prevVal - 1) + ct.name());
//                    System.out.println(Card.valueOf("_2H"));
                    possibleCardsPrevInColumn.add(Card.valueOf("_" + cardNos.charAt(prevVal - 1) + ct.name()));
//                    System.out.println(":" + Card.valueOf("_" + cardNos.charAt(prevVal - 1) + ct.name()));
                }catch(IllegalArgumentException ex){   
                }
            }
        }
        if(nextVal - 1 >= 0 && nextVal - 1 < 13){
            for(CardType ct : prevOrNextCardTypes){
                try{
                    possibleCardsNextInColumn.add(Card.valueOf("_" + cardNos.charAt(nextVal - 1) + ct.name()));
                }catch(IllegalArgumentException ex){   
                }
            }
        }
        
        possibleCardsPrevInColumn = Collections.unmodifiableList(possibleCardsPrevInColumn);
        possibleCardsNextInColumn = Collections.unmodifiableList(possibleCardsNextInColumn);
    }
    
    public int val(){
        return val;
    }
    
    public CardType type(){
        return type;
    }
    
    public boolean isSameType(Card card){
        return this.type.equals(card.type());
    }

    public List<Card> getPossibleCardsPrevInColumn() {
        return possibleCardsPrevInColumn;
    }

    public List<Card> getPossibleCardsNextInColumn() {
        return possibleCardsNextInColumn;
    }
}
