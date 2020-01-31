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
        board.addCard(Columns._1, Cards._QC);
        board.addCard(Columns._1, Cards._3H);
        board.addCard(Columns._1, Cards._AH);
        board.addCard(Columns._1, Cards._5D);
        board.addCard(Columns._1, Cards._8C);
        board.addCard(Columns._1, Cards._6H);
        board.addCard(Columns._1, Cards._9H);
        
        board.addCard(Columns._2, Cards._AD);
        board.addCard(Columns._2, Cards._4S);
        board.addCard(Columns._2, Cards._6C);
        board.addCard(Columns._2, Cards._7C);
        board.addCard(Columns._2, Cards._TS);
        board.addCard(Columns._2, Cards._JD);
        board.addCard(Columns._2, Cards._JH);
        
/**
 *
 * C black round tree
 * D red square
 * S black angle tree
 * H red heart
 * @author pairach.g
 */
        board.addCard(Columns._3, Cards._5S);
        board.addCard(Columns._3, Cards._5H);
        board.addCard(Columns._3, Cards._4H);
        board.addCard(Columns._3, Cards._5C);
        board.addCard(Columns._3, Cards._7S);
        board.addCard(Columns._3, Cards._2H);
        board.addCard(Columns._3, Cards._QS);
        
        board.addCard(Columns._4, Cards._9S);
        board.addCard(Columns._4, Cards._4D);
        board.addCard(Columns._4, Cards._TH);
        board.addCard(Columns._4, Cards._6S);
        board.addCard(Columns._4, Cards._8D);
        board.addCard(Columns._4, Cards._KD);
        board.addCard(Columns._4, Cards._2C);
        
        board.addCard(Columns._5, Cards._KH);
        board.addCard(Columns._5, Cards._2D);
        board.addCard(Columns._5, Cards._7D);
        board.addCard(Columns._5, Cards._3S);
        board.addCard(Columns._5, Cards._TC);
        board.addCard(Columns._5, Cards._JC);
        
        board.addCard(Columns._6, Cards._KS);
        board.addCard(Columns._6, Cards._3D);
        board.addCard(Columns._6, Cards._AC);
        board.addCard(Columns._6, Cards._QH);
        board.addCard(Columns._6, Cards._8S);
        board.addCard(Columns._6, Cards._AS);
        
        board.addCard(Columns._7, Cards._6D);
        board.addCard(Columns._7, Cards._TD);
        board.addCard(Columns._7, Cards._9D);
        board.addCard(Columns._7, Cards._4C);
        board.addCard(Columns._7, Cards._8H);
        board.addCard(Columns._7, Cards._QD);
        
        board.addCard(Columns._8, Cards._2S);
        board.addCard(Columns._8, Cards._7H);
        board.addCard(Columns._8, Cards._9C);
        board.addCard(Columns._8, Cards._JS);
        board.addCard(Columns._8, Cards._KC);
        board.addCard(Columns._8, Cards._3C);
        
        /**
         * end Game #142744 free-cell by Mobility ware
         */
        System.out.println("board.isBoardValid():" + board.isBoardValid());
        board.showLastCardOfEachColumns();
    }
}
