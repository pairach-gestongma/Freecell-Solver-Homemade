/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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
    
    private List<ColumnCard> allColumns;
    private List<Foundation> allFoundations;
    private List<Card> allFreeCells;

    private CardComparator cardsComparator;
    
    private final GameBoard parent;
    private final int depth;
    private boolean isOver;
    private String name;
    private int boardId;

    private GameBoard(int boardId, String name, GameBoard parent,int depth) {
//        System.out.println("name:" + name);
        this.boardId = boardId;
        this.name = name;
        this.parent = parent;
        this.depth = depth;
        isOver = false;
        
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
    
    public GameBoard(int boardId, String spec, String name, GameBoard parent,int depth){
        this(boardId, name, parent, depth);
//        System.out.println("spec:");
//        System.out.println(spec);
        String[] lines = spec.split("\n");
        for(int i=0;i<8;i++){
            
            int lineNo = 0;
            
            B:
            for(String line : lines){
//                System.out.println(lineNo+":" + line);
                if(0 == lineNo++){
                    String cardName = line.substring(i*4, i*4 + 3);
    //                System.out.println("cardName:" + cardName);
                    if(cardName.trim().length() == 0){
                        
                    }else{
                        if(i<=3){
                            allFreeCells.add(
                                Card.valueOf(cardName));
                        }else{
                            Card c = Card.valueOf(cardName);
                            for(Foundation f : allFoundations){
                                if(f.cardType().equals(c.type())){
                                    f.initWithCard(c);
                                }
                            }
                        }
                    }
                    continue B;
                }
                String cardName = line.substring(i*4, i*4 + 3);
//                System.out.println("cardName:" + cardName);
                if(cardName.trim().length() == 0){
                    continue;
                }
                allColumns.get(i).getCards().add(
                        Card.valueOf(cardName));
            }// \. for(String line : lines){
        }
    }
    
    public GameBoard getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }
    
    public boolean isFullFreeCell(){
        return allFreeCells.size() == 4;
    }

    public List<Foundation> getAllFoundations() {
        return allFoundations;
    }
    
    public void addCardToFreeCell(Card card, ColumnNo fromCol) {
        if(isFullFreeCell()){
            throw new RuntimeException("can not add card, freecell already full!");
        }
        allFreeCells.add(card);
        getColumn(fromCol).getCards().remove(card);
    }
    
    public void moveCard(ColumnAndCard colAndCard){
        ColumnCard toCol = getColumn(colAndCard.getMoveToCol().getColumn());
        toCol.getCards().add(colAndCard.getCard());
        ColumnCard srcCol = getColumn(colAndCard.getColumn().getColumn());
        srcCol.getCards().remove(colAndCard.getCard());
    }
    
    public void moveCardFromFreecell(ColumnAndCard colAndCard){
        ColumnCard toCol = getColumn(colAndCard.getMoveToCol().getColumn());
        toCol.getCards().add(colAndCard.getCard());
        allFreeCells.remove(colAndCard.getCard());
    }
    
    public void moveCards(ColumnAndCard colAndCard){
        ColumnCard toCol = getColumn(colAndCard.getMoveToCol().getColumn());
        ColumnCard srcCol = getColumn(colAndCard.getColumn().getColumn());
        for(Card c : colAndCard.getCards()){
            toCol.getCards().add(c);
            srcCol.getCards().remove(c);
        }
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
    
    public String getBoardStateFreecellAndFoundation(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++){
            try{
                sb.append(allFreeCells.get(i) + " ");
            }catch(IndexOutOfBoundsException ex){
                sb.append("   " + " ");
            }
        }
        for(int i=0;i<4;i++){
            try{
                sb.append(allFoundations.get(i).currentCard() + " ");
            }catch(IndexOutOfBoundsException ex){
                sb.append("   " + " ");
            }catch(NoSuchElementException ex){
                sb.append("   " + " ");
            }
        }
        sb.append("\n");
        
        int maxCardByEachColumns = 0;
        for(ColumnCard col : allColumns){
            maxCardByEachColumns = Math.max(maxCardByEachColumns, col.getCards().size());
        }
        
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
    
    public void printBoardState() {
        System.out.println(lineSp);
        System.out.println("showBoard : "+boardId+"(" + name
                + ","+((parent!=null)?parent.boardId:"")+"), depth:" + depth);
        System.out.println(lineSp);
        for(int i=0;i<4;i++){
            try{
                System.out.print(allFreeCells.get(i) + " ");
            }catch(IndexOutOfBoundsException ex){
                System.out.print("   " + " ");
            }
        }
        for(int i=0;i<4;i++){
            try{
                System.out.print(allFoundations.get(i).currentCard() + " ");
            }catch(IndexOutOfBoundsException ex){
                System.out.print("   " + " ");
            }catch(NoSuchElementException ex){
                System.out.print("   " + " ");
            }
        }
        System.out.println();
        System.out.println(lineSp.replaceAll("-", "."));
        System.out.println(getBoardState());
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
    
    public List<ColumnAndCard> getCardsPossibleToMoveToFoundation(){
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
    
    public List<ColumnAndCard> getCardsPossibleToMoveToFreecell(){
        List<ColumnAndCard> possibleCards = new ArrayList();
        if(isFullFreeCell()){
            return possibleCards;
        }
        List<ColumnAndCard> lastCardFromEachColumns = getLastCardFromEachColumns();
        for(ColumnAndCard colAndCard : lastCardFromEachColumns){
            possibleCards.add(colAndCard);
        }
        return possibleCards;
    }
    
    public List<ColumnAndCard> getCardsPossibleToMoveFromColumnToColumn(){
        List<ColumnAndCard> res = new ArrayList();
        List<ColumnAndCard> lastCardFromEachColumns = getLastCardFromEachColumns();
        for(ColumnAndCard colCard1 : lastCardFromEachColumns){
            B:
            for(ColumnAndCard colCard2 : lastCardFromEachColumns){
                if(colCard1.equals(colCard2)){
                    continue B;
                }
                if(colCard1.getCard().getPossibleCardsNextInColumn()
                        .contains(colCard2.getCard())){
                    ColumnAndCard re = new ColumnAndCard(colCard2.getColumn(), colCard2.getCard()
                            , colCard1.getColumn(), colCard1.getCard());
                    res.add(re);
                }
            }
        }
        return res;
    }
    
    public ColumnCard getColumn(ColumnNo no){
        for(ColumnCard col : allColumns){
            if(col.getColumn().equals(no)){
                return col;
            }
        }
        return null;
    }
    
    public List<ColumnAndCard> getPairColumns(){
        List<ColumnAndCard> res = new ArrayList();
        List<ColumnAndCard> lastCardFromEachColumns = getLastCardFromEachColumns();
        for(ColumnAndCard lastCard : lastCardFromEachColumns){
            List<Card> pairs = new ArrayList();
            Card last = null;
            B:
            for(int i=lastCard.getColumn().getCards().size();i>0;i--){
                Card c = lastCard.getColumn().getCards().get(i -1);
                if(i==lastCard.getColumn().getCards().size()){
                    last = c;
                    pairs.add(last);
                    continue B;
                }
//                System.out.println("last:" + last.name());
//                System.out.println("up:" + c.name());
//                for(Card prevCard : last.getPossibleCardsPrevInColumn()){
//                    System.out.println("prevCard:" + prevCard.name());
//                }
                if(last.getPossibleCardsPrevInColumn().contains(c)){
                    pairs.add(c);
                }else{
                    break B;
                }
            }
            if(pairs.size() >= 2){
                Collections.reverse(pairs);
                ColumnAndCard ccRes = new ColumnAndCard(lastCard.getColumn(),
                        pairs, null, null);
                res.add(ccRes);
            }
        }
        return res;
    }
    
    public List<ColumnAndCard> getPairCardPossibleToMoveToColumn(){
        List<ColumnAndCard> res = new ArrayList();
        List<ColumnAndCard> pairColumns = getPairColumns();
        for(ColumnAndCard pairColumn : pairColumns){
            List<ColumnAndCard> lastCardFromEachColumns = getLastCardFromEachColumns();
            for(ColumnAndCard lastCard : lastCardFromEachColumns){
                if(pairColumn.getColumn().getColumn().equals(
                    lastCard.getColumn().getColumn())){
                    continue;
                }
                B:
                for(Card c : pairColumn.getCards()){
                    if(pairColumn.getCards().indexOf(c)+1 == pairColumn.getCards().size()){
                        break B;
                    }
                    if(lastCard.getCard().getPossibleCardsNextInColumn().contains(c)){
                        List<Card> pairCardsToMove = new ArrayList();
                        for(int i=pairColumn.getCards().indexOf(c);i<pairColumn.getCards().size();i++){
                            pairCardsToMove.add(pairColumn.getCards().get(i));
                        }
                        ColumnAndCard re = new ColumnAndCard(pairColumn.getColumn(), 
                                pairCardsToMove, lastCard.getColumn(), lastCard.getCard());
                        res.add(re);
                    }
                }
            }
        }
        return res;
    }
    
    public List<Card> getFreecellCardPossibleToMoveToFoundation(){
        List<Card> res = new ArrayList();
        for(Card card : allFreeCells){
            B:
            for(Foundation fdtn : allFoundations){
                if(!fdtn.cardType().equals(card.type())){
                    continue B;
                }
                try{
                    if(fdtn.currentCard().val() + 1 == card.val()){
                        res.add(card);
                    }   
                }catch(NoSuchElementException ex){
                }
                break B;
            }
        }
        return res;
    }
    
    public List<ColumnAndCard> getFreecellCardPossibleToMoveToColumn(){
        List<ColumnAndCard> res = new ArrayList();
        for(Card card : allFreeCells){
            for(ColumnAndCard cc : getLastCardFromEachColumns()){
                if(cc.getCard().getPossibleCardsNextInColumn()
                        .contains(card)){
                    ColumnAndCard re = new ColumnAndCard(null, card, 
                            cc.getColumn(), card);
                    res.add(re);
                }
            }
        }
        return res;
    }
    
    public List<Card> getAllFreeCells() {
        return allFreeCells;
    }
    
//    public List<GameBoard> nextPlay(){
//        List<GameBoard> res = new ArrayList();
//        GameBoard gb = new GameBoard(getBoardState().toString());
//        // posible to foundation
//        List<ColumnAndCard> cards = gb.getCardsPossibleToMoveToFoundation();
//        A:
//        for(ColumnAndCard colAndCard : cards){
//            B:
//            for(Foundation fd : gb.allFoundations){
//                if(fd.cardType().equals(colAndCard.getCard().type())){
//                    fd.addCard(colAndCard.getCard(), colAndCard.getColumn());
//                    break B;
//                }
//            }
//            res.add(new GameBoard(gb.getBoardState()));
//        }
//        // \. posible to foundation
//        return res;
//    }

    
}
