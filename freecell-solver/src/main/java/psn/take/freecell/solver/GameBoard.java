/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author pairach.g
 */
public class GameBoard {

    private Foundation foundationC;
    private Foundation foundationD;
    private Foundation foundationH;
    private Foundation foundationS;
    private ColumnCard column1;
    private ColumnCard column2;
    private ColumnCard column3;
    private ColumnCard column4;
    private ColumnCard column5;
    private ColumnCard column6;
    private ColumnCard column7;
    private ColumnCard column8;
    
    List<ColumnCard> allColumns;
    List<Foundation> allFoundations;
    List<Card> allFreeCells;

    private CardComparator cardsComparator;
    
    public GameBoard() {
        foundationC = new Foundation(CardType.C);
        foundationD = new Foundation(CardType.D);
        foundationH = new Foundation(CardType.H);
        foundationS = new Foundation(CardType.S);

        column1 = new ColumnCard(ColumnNo._1);
        column2 = new ColumnCard(ColumnNo._2);
        column3 = new ColumnCard(ColumnNo._3);
        column4 = new ColumnCard(ColumnNo._4);
        column5 = new ColumnCard(ColumnNo._5);
        column6 = new ColumnCard(ColumnNo._6);
        column7 = new ColumnCard(ColumnNo._7);
        column8 = new ColumnCard(ColumnNo._8);
        
        allColumns = new ArrayList();
        allColumns.add(column1);
        allColumns.add(column2);
        allColumns.add(column3);
        allColumns.add(column4);
        allColumns.add(column5);
        allColumns.add(column6);
        allColumns.add(column7);
        allColumns.add(column8);
        
        allFoundations = new ArrayList();
        allFoundations.add(foundationC);
        allFoundations.add(foundationD);
        allFoundations.add(foundationH);
        allFoundations.add(foundationS);
        
        allFreeCells = new ArrayList();
        
        cardsComparator = new CardComparator();
    }
    
    public GameBoard(StringBuilder spec){
        this();
        String[] lines = spec.toString().split("\n");
        for(int i=0;i<8;i++){
            for(String line : lines){
                String cardName = line.substring(i*4, i*4 + 3);
//                System.out.println("cardName:" + cardName);
                if(cardName.trim().length() == 0){
                    continue;
                }
                allColumns.get(i).getCards().add(
                        Card.valueOf(cardName));
            }
        }
    }
    
    public boolean isFullFreeCell(){
        return allFreeCells.size() == 4;
    }
    
    public void addCardToFreeCell(Card card) {
        if(isFullFreeCell()){
            throw new RuntimeException("can not add card, freecell already full!");
        }
        allFreeCells.add(card);
    }
    
    public void intialPrevNextAllCards(){
        allColumns.forEach((ColumnCard col) -> {
            col.getCards().forEach((card) -> card.initialPrevNext());
        });
    }

    public void addCard(ColumnNo col, Card card){
        allColumns.forEach((ColumnCard col1) -> {
            if(col1.getColumn().equals(col)){
                col1.getCards().add(card);
            }
        });
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
        if((column1.getCards().size() + column2.getCards().size()
                + column3.getCards().size() + column4.getCards().size()
                + column5.getCards().size() + column6.getCards().size()
                + column7.getCards().size() + column8.getCards().size()) != 13*4){
            return false;
        }
        
        List<Card> cCards = new ArrayList();
        List<Card> dCards = new ArrayList();
        List<Card> sCards = new ArrayList();
        List<Card> hCards = new ArrayList();
        collectCard(CardType.C, cardsFromAllColumns(), cCards);
        collectCard(CardType.D, cardsFromAllColumns(), dCards);
        collectCard(CardType.S, cardsFromAllColumns(), sCards);
        collectCard(CardType.H, cardsFromAllColumns(), hCards);
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
            for(Card c : cCards){
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
        if(!checkSequenceIsCorrect(cCards)
                || !checkSequenceIsCorrect(dCards)
                || !checkSequenceIsCorrect(sCards)
                || !checkSequenceIsCorrect(hCards)){
            return false;
        }
        
        return true;
    } // \. isBoardValid()
    
    public boolean checkSequenceIsCorrect(List<Card> cards){
        for(int i=0;i<13;i++){
            if( cards.get(i).val() != i + 1 ){
                return false;
            }
        }
        return true;
    }
    
    public List<Card> cardsFromAllColumns(){
        List<Card> ret = new ArrayList();
        ret.addAll(column1.getCards());
        ret.addAll(column2.getCards());
        ret.addAll(column3.getCards());
        ret.addAll(column4.getCards());
        ret.addAll(column5.getCards());
        ret.addAll(column6.getCards());
        ret.addAll(column7.getCards());
        ret.addAll(column8.getCards());
        return ret;
    }
    
    private void collectCard(CardType need, List<Card> src, List<Card> dest){
        for(Card card : src){
            if(card.type().equals(need)){
                dest.add(card);
            }
        }
    }
    
    public static final String lineSp = "---------------------------------------"
            + "---------------------------------";
    
    public String getBoardState(){
//        System.out.println(lineSp);
//        System.out.println("showBoard");
//        System.out.println(lineSp);

        int maxCardByEachColumns = 0;
        for(ColumnCard col : allColumns){
            maxCardByEachColumns = Math.max(maxCardByEachColumns, col.getCards().size());
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<maxCardByEachColumns;i++){
            for(ColumnCard col : allColumns){
                try{
                    sb.append(col.getCards().get(i));
                    sb.append(" ");
                }catch(IndexOutOfBoundsException ex){
                    sb.append("   ");
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private List<ColumnAndCard> getLastCardFromEachColumns(){
        List<ColumnAndCard> lastCards = new ArrayList();
        lastCards.add(new ColumnAndCard(column1, column1.getCards().get(column1.getCards().size() - 1)));
        lastCards.add(new ColumnAndCard(column2, column2.getCards().get(column2.getCards().size() - 1)));
        lastCards.add(new ColumnAndCard(column3, column3.getCards().get(column3.getCards().size() - 1)));
        lastCards.add(new ColumnAndCard(column4, column4.getCards().get(column4.getCards().size() - 1)));
        lastCards.add(new ColumnAndCard(column5, column5.getCards().get(column5.getCards().size() - 1)));
        lastCards.add(new ColumnAndCard(column6, column6.getCards().get(column6.getCards().size() - 1)));
        lastCards.add(new ColumnAndCard(column7, column7.getCards().get(column7.getCards().size() - 1)));
        lastCards.add(new ColumnAndCard(column8, column8.getCards().get(column8.getCards().size() - 1)));
        return lastCards;
    }
    
    public void showLastCardOfEachColumns(){
        System.out.println(lineSp);
        System.out.println("showLastCardOfEachColumns");
        System.out.println(lineSp);
        List<ColumnAndCard> lastCardFromEachColumns = getLastCardFromEachColumns();
        for(ColumnAndCard colAndCard : lastCardFromEachColumns){
            System.out.print("column" + (lastCardFromEachColumns.indexOf(colAndCard) 
                    + 1) + ":" + colAndCard);
            System.out.print(" ");
            for(Card prevCard : colAndCard.getCard().getPossibleCardsPrevInColumn()){
                if(colAndCard.getCard().getPossibleCardsPrevInColumn().indexOf(prevCard) > 0){
                    System.out.print(",");
                }
                System.out.print(prevCard);
            }
            System.out.print(" ");
            for(Card prevCard : colAndCard.getCard().getPossibleCardsNextInColumn()){
                if(colAndCard.getCard().getPossibleCardsPrevInColumn().indexOf(prevCard) > 0){
                    System.out.print(",");
                }
                System.out.print(prevCard);
            }
            System.out.println();
        }
    }
    
    public void showCardsPossibleToMoveToFoundation(){
        System.out.println(lineSp);
        System.out.println("showCardsPossibleToMoveToFoundation");
        System.out.println(lineSp);
        List<ColumnAndCard> lastCardFromEachColumns = getCardsPossibleToMoveToFoundation();
        for(ColumnAndCard colAndCard : lastCardFromEachColumns){
            System.out.println("column" + (lastCardFromEachColumns.indexOf(colAndCard) 
                    + 1) + ":" + colAndCard.getCard());
        }
    }
    
    private List<ColumnAndCard> getCardsPossibleToMoveToFoundation(){
        List<ColumnAndCard> possibleCards = new ArrayList();
        List<ColumnAndCard> lastCardFromEachColumns = getLastCardFromEachColumns();
        for(Foundation fdtn : allFoundations){
            for(ColumnAndCard colAndCard : lastCardFromEachColumns){
                if(fdtn.nextNeedCard().equals(colAndCard.getCard())){
                    possibleCards.add(colAndCard);
                }
            }
        }
        return possibleCards;
    }
    
    public List<String> nextPlay(){
        List<String> res = new ArrayList();
        List<ColumnAndCard> cards = getCardsPossibleToMoveToFoundation();
        A:
        for(ColumnAndCard colAndCard : cards){
            for(Foundation fd : allFoundations){
                if(fd.cardType().equals(colAndCard.getCard().type())){
                    fd.addCard(colAndCard.getCard(), colAndCard.getColumn());
                    continue A;
                }
            }
        }
        return res;
    }
}
