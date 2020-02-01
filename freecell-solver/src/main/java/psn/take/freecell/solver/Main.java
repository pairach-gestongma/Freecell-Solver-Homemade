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
        board.addCard(ColumnNo._1, Card._QC);
        board.addCard(ColumnNo._1, Card._3H);
        board.addCard(ColumnNo._1, Card._AH);
        board.addCard(ColumnNo._1, Card._5D);
        board.addCard(ColumnNo._1, Card._8C);
        board.addCard(ColumnNo._1, Card._6H);
        board.addCard(ColumnNo._1, Card._9H);
        
        board.addCard(ColumnNo._2, Card._AD);
        board.addCard(ColumnNo._2, Card._4S);
        board.addCard(ColumnNo._2, Card._6C);
        board.addCard(ColumnNo._2, Card._7C);
        board.addCard(ColumnNo._2, Card._TS);
        board.addCard(ColumnNo._2, Card._JD);
        board.addCard(ColumnNo._2, Card._JH);
        
/**
 *
 * C black round tree
 * D red square
 * S black angle tree
 * H red heart
 * @author pairach.g
 */
        board.addCard(ColumnNo._3, Card._5S);
        board.addCard(ColumnNo._3, Card._5H);
        board.addCard(ColumnNo._3, Card._4H);
        board.addCard(ColumnNo._3, Card._5C);
        board.addCard(ColumnNo._3, Card._7S);
        board.addCard(ColumnNo._3, Card._2H);
        board.addCard(ColumnNo._3, Card._QS);
        
        board.addCard(ColumnNo._4, Card._9S);
        board.addCard(ColumnNo._4, Card._4D);
        board.addCard(ColumnNo._4, Card._TH);
        board.addCard(ColumnNo._4, Card._6S);
        board.addCard(ColumnNo._4, Card._8D);
        board.addCard(ColumnNo._4, Card._KD);
        board.addCard(ColumnNo._4, Card._2C);
        
        board.addCard(ColumnNo._5, Card._KH);
        board.addCard(ColumnNo._5, Card._2D);
        board.addCard(ColumnNo._5, Card._7D);
        board.addCard(ColumnNo._5, Card._3S);
        board.addCard(ColumnNo._5, Card._TC);
        board.addCard(ColumnNo._5, Card._JC);
        
        board.addCard(ColumnNo._6, Card._KS);
        board.addCard(ColumnNo._6, Card._3D);
        board.addCard(ColumnNo._6, Card._AC);
        board.addCard(ColumnNo._6, Card._QH);
        board.addCard(ColumnNo._6, Card._8S);
        board.addCard(ColumnNo._6, Card._AS);
        
        board.addCard(ColumnNo._7, Card._6D);
        board.addCard(ColumnNo._7, Card._TD);
        board.addCard(ColumnNo._7, Card._9D);
        board.addCard(ColumnNo._7, Card._4C);
        board.addCard(ColumnNo._7, Card._8H);
        board.addCard(ColumnNo._7, Card._QD);
        
        board.addCard(ColumnNo._8, Card._2S);
        board.addCard(ColumnNo._8, Card._7H);
        board.addCard(ColumnNo._8, Card._9C);
        board.addCard(ColumnNo._8, Card._JS);
        board.addCard(ColumnNo._8, Card._KC);
        board.addCard(ColumnNo._8, Card._3C);
        
        /**
         * end Game #142744 free-cell by Mobility ware
         */
        System.out.println("board.isBoardValid():" + board.isBoardValid());
        board.intialPrevNextAllCards();
        
        //board.getBoardState();
        //board.showLastCardOfEachColumns();
        //board.showCardsPossibleToMoveToFoundation();
        
//        board.nextPlay();
//        board.getBoardState();

        StringBuilder sb = new StringBuilder();
sb.append("_QC _AD _5S _9S _KH _KS _6D _2S \n");
sb.append("_3H _4S _5H _4D _2D _3D _TD _7H \n");
sb.append("_AH _6C _4H _TH _7D _AC _9D _9C \n");
sb.append("_5D _7C _5C _6S _3S _QH _4C _JS \n");
sb.append("_8C _TS _7S _8D _TC _8S _8H _KC \n");
sb.append("_6H _JD _2H _KD _JC _AS _QD _3C \n");
sb.append("_9H _JH _QS _2C                 \n");
        GameBoard board2 = new GameBoard(sb);
        board2.getBoardState();
        System.out.println(board.getBoardState().equals(board2.getBoardState()));
    }
}
