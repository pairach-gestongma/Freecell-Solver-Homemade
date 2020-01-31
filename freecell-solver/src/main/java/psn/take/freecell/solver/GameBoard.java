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
 * @author pairach.g
 */
public class GameBoard {

    private Cards freeCell1;
    private Cards freeCell2;
    private Cards freeCell3;
    private Cards freeCell4;
    private List<Cards> foundation1;
    private List<Cards> foundation2;
    private List<Cards> foundation3;
    private List<Cards> foundation4;
    private List<Cards> column1;
    private List<Cards> column2;
    private List<Cards> column3;
    private List<Cards> column4;
    private List<Cards> column5;
    private List<Cards> column6;
    private List<Cards> column7;
    private List<Cards> column8;

    private CardsComparator cardsComparator;
    
    public GameBoard() {
        foundation1 = new ArrayList();
        foundation2 = new ArrayList();
        foundation3 = new ArrayList();
        foundation4 = new ArrayList();

        column1 = new ArrayList();
        column2 = new ArrayList();
        column3 = new ArrayList();
        column4 = new ArrayList();
        column5 = new ArrayList();
        column6 = new ArrayList();
        column7 = new ArrayList();
        column8 = new ArrayList();
        
        cardsComparator = new CardsComparator();
    }

    public void addCard(Columns col, Cards card){
        switch (col) {
            case _1:
                column1.add(card);
                break;
            case _2:
                column2.add(card);
                break;
            case _3:
                column3.add(card);
                break;
            case _4:
                column4.add(card);
                break;
            case _5:
                column5.add(card);
                break;
            case _6:
                column6.add(card);
                break;
            case _7:
                column7.add(card);
                break;
            case _8:
                column8.add(card);
                break;
            default:
                break;
        }
    }
    
/**
 *
 * C black round tree
 * D red square
 * S black angle tree
 * H red heart
 * @author pairach.g
 */
    public boolean isBoardValid(){
        if((column1.size() + column2.size()
                + column3.size() + column4.size()
                + column5.size() + column6.size()
                + column7.size() + column8.size()) != 13*4){
            return false;
        }
        
        List<Cards> cCards = new ArrayList();
        List<Cards> dCards = new ArrayList();
        List<Cards> sCards = new ArrayList();
        List<Cards> hCards = new ArrayList();
        collectCard(CardTypes.C, cardsFromAllColumns(), cCards);
        collectCard(CardTypes.D, cardsFromAllColumns(), dCards);
        collectCard(CardTypes.S, cardsFromAllColumns(), sCards);
        collectCard(CardTypes.H, cardsFromAllColumns(), hCards);
        if(cCards.size() != 13
                || dCards.size() != 13
                || sCards.size() != 13
                || hCards.size() != 13){
            System.out.println("cards size not valid");
            cCards.sort(cardsComparator);
            dCards.sort(cardsComparator);
            sCards.sort(cardsComparator);
            hCards.sort(cardsComparator);
            System.out.print("c:" + cCards.size());
            for(Cards c : cCards){
                if(cCards.indexOf(c) > 0){
                    System.out.print(",");
                }
                System.out.print(c.toString());
            }
            System.out.println();
            System.out.print("d:" + dCards.size());
            System.out.print("s:" + sCards.size());
            System.out.print("h:" + hCards.size());
            return false;
        }
        
        cCards.sort(cardsComparator);
        dCards.sort(cardsComparator);
        sCards.sort(cardsComparator);
        hCards.sort(cardsComparator);
        if(!checkSequence(cCards)
                || !checkSequence(dCards)
                || !checkSequence(sCards)
                || !checkSequence(hCards)){
            return false;
        }
        
        return true;
    }
    
    public boolean checkSequence(List<Cards> cards){
        for(int i=0;i<13;i++){
            if( cards.get(i).val() != i + 1 ){
                return false;
            }
        }
        return true;
    }
    
    public List<Cards> cardsFromAllColumns(){
        List<Cards> ret = new ArrayList();
        ret.addAll(column1);
        ret.addAll(column2);
        ret.addAll(column3);
        ret.addAll(column4);
        ret.addAll(column5);
        ret.addAll(column6);
        ret.addAll(column7);
        ret.addAll(column8);
        return ret;
    }
    
    private void collectCard(CardTypes need, List<Cards> src, List<Cards> dest){
        for(Cards card : src){
            if(card.type().equals(need)){
                dest.add(card);
            }
        }
    }
    
    public void showLastCardOfEachColumns(){
        System.out.println("column1:" + column1.get(column1.size() - 1));
        System.out.println("column2:" + column2.get(column2.size() - 1));
        System.out.println("column3:" + column3.get(column3.size() - 1));
        System.out.println("column4:" + column4.get(column4.size() - 1));
        System.out.println("column5:" + column5.get(column5.size() - 1));
        System.out.println("column6:" + column6.get(column6.size() - 1));
        System.out.println("column7:" + column7.get(column7.size() - 1));
        System.out.println("column8:" + column8.get(column8.size() - 1));
    }
}
