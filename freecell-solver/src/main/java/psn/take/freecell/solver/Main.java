/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

/**
 *
 * C black round tree
 * D red square
 * S black angle tree
 * H red heart
 * @author pairach.g
 */
public class Main {
    
    public static void main(String[] args) {
        
        GameBoard board = new GameBoard();
        
        /**
         * Game #142744 free-cell by Mobility ware
         */
        board.addCard(Columns._1, Card._QC);
        board.addCard(Columns._1, Card._3H);
        board.addCard(Columns._1, Card._AH);
        board.addCard(Columns._1, Card._5D);
        board.addCard(Columns._1, Card._8C);
        board.addCard(Columns._1, Card._6H);
        board.addCard(Columns._1, Card._9H);
        
        board.addCard(Columns._2, Card._AD);
        board.addCard(Columns._2, Card._4S);
        board.addCard(Columns._2, Card._6C);
        board.addCard(Columns._2, Card._7C);
        board.addCard(Columns._2, Card._TS);
        board.addCard(Columns._2, Card._JD);
        board.addCard(Columns._2, Card._JH);
        
/**
 *
 * C black round tree
 * D red square
 * S black angle tree
 * H red heart
 * @author pairach.g
 */
        board.addCard(Columns._3, Card._5S);
        board.addCard(Columns._3, Card._5H);
        board.addCard(Columns._3, Card._4H);
        board.addCard(Columns._3, Card._5C);
        board.addCard(Columns._3, Card._7S);
        board.addCard(Columns._3, Card._2H);
        board.addCard(Columns._3, Card._QS);
        
        board.addCard(Columns._4, Card._9S);
        board.addCard(Columns._4, Card._4D);
        board.addCard(Columns._4, Card._TH);
        board.addCard(Columns._4, Card._6S);
        board.addCard(Columns._4, Card._8D);
        board.addCard(Columns._4, Card._KD);
        board.addCard(Columns._4, Card._2C);
        
        board.addCard(Columns._5, Card._KH);
        board.addCard(Columns._5, Card._2D);
        board.addCard(Columns._5, Card._7D);
        board.addCard(Columns._5, Card._3S);
        board.addCard(Columns._5, Card._TC);
        board.addCard(Columns._5, Card._JC);
        
        board.addCard(Columns._6, Card._KS);
        board.addCard(Columns._6, Card._3D);
        board.addCard(Columns._6, Card._AC);
        board.addCard(Columns._6, Card._QH);
        board.addCard(Columns._6, Card._8S);
        board.addCard(Columns._6, Card._AS);
        
        board.addCard(Columns._7, Card._6D);
        board.addCard(Columns._7, Card._TD);
        board.addCard(Columns._7, Card._9D);
        board.addCard(Columns._7, Card._4C);
        board.addCard(Columns._7, Card._8H);
        board.addCard(Columns._7, Card._QD);
        
        board.addCard(Columns._8, Card._2S);
        board.addCard(Columns._8, Card._7H);
        board.addCard(Columns._8, Card._9C);
        board.addCard(Columns._8, Card._JS);
        board.addCard(Columns._8, Card._KC);
        board.addCard(Columns._8, Card._3C);
        
        /**
         * end Game #142744 free-cell by Mobility ware
         */
        System.out.println("board.isBoardValid():" + board.isBoardValid());
        board.showLastCardOfEachColumns();
        new Foundation(CardType.C);
    }
}
