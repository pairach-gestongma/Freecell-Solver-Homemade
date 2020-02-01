/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.List;

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
        /**
         * Game #142744 free-cell by Mobility ware
         */
        StringBuilder sb = new StringBuilder();
sb.append("_QC _AD _5S _9S _KH _KS _6D _2S \n");
sb.append("_3H _4S _5H _4D _2D _3D _TD _7H \n");
sb.append("_AH _6C _4H _TH _7D _AC _9D _9C \n");
sb.append("_5D _7C _5C _6S _3S _QH _4C _JS \n");
sb.append("_8C _TS _7S _8D _TC _8S _8H _KC \n");
sb.append("_6H _JD _2H _KD _JC _AS _QD _3C \n");
sb.append("_9H _JH _QS _2C                 \n");
        GameBoard board = new GameBoard(sb.toString());        
        board.intialPrevNextAllCards(); // call once per runtime
        /**
         * end Game #142744 free-cell by Mobility ware
         */
        System.out.println("board.isBoardValid():" + board.isBoardValid());
        board.printBoardState();
        
        // foundation
        List<ColumnAndCard> cards = board.getCardsPossibleToMoveToFoundation();
        for(ColumnAndCard cc : cards){
            GameBoard newBoard = new GameBoard(board.getBoardState());
            B:
            for(Foundation fd : newBoard.allFoundations){
                if(fd.cardType().equals(cc.getCard().type())){
                    fd.addCard(cc.getCard(), newBoard.getColumn(cc.getColumn().getColumn()));
                    break B;
                }
            }
            newBoard.printBoardState();
        }
        // \. foundation
        // freecell
        cards = board.getCardsPossibleToMoveToFreecell();
        for(ColumnAndCard cc : cards){
            GameBoard newBoard = new GameBoard(board.getBoardState());
            newBoard.addCardToFreeCell(cc.getCard(), cc.getColumn().getColumn());
            newBoard.printBoardState();
        }
        // \. freecell
    }
}
