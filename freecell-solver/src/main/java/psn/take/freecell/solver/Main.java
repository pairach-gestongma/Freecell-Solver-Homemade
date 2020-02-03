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
 * C black round tree
 * D red square
 * S black angle tree
 * H red heart
 * @author pairach.g
 */
public class Main {
    
    
    
    public static void main1(String[] args) {
        /**
         * initial Game #142744 free-cell by Mobility ware
         */
        int boardId = 0;
        StringBuilder sb = new StringBuilder();
sb.append("                                \n");
sb.append("_QC _AD _5S _9S _KH _KS _6D _2S \n");
sb.append("_3H _4S _5H _4D _2D _3D _TD _7H \n");
sb.append("_AH _6C _4H _TH _7D _AC _9D _9C \n");
sb.append("_5D _7C _5C _6S _3S _QH _4C _JS \n");
sb.append("_8C _TS _7S _8D _TC _8S _8H _KC \n");
sb.append("_6H _JD _2H _KD _JC _AS _QD _3C \n");
sb.append("_9H _JH _QS _2C                 \n");
        GameBoard board = new GameBoard(boardId++, sb.toString(), "#142744", null, 0);        
        board.intialPrevNextAllCards(); // call once per runtime
        /**
         * end initial Game #142744 free-cell by Mobility ware
         */
        System.out.println("board.isBoardValid():" + board.isBoardValid());
        board.printBoardState();
        //System.out.println(board.getBoardStateFreecellAndFoundation());
        
        List<String> memory = new ArrayList();
        List<GameBoard> toPlays = new ArrayList();
        toPlays.add(board);
        memory.add(board.getBoardState());
        for(int i=0;i<4;i++){
            
            List<GameBoard> toPlaysNext = new ArrayList();
            for(GameBoard playingBoard : toPlays){
                        
                // foundation
                List<ColumnAndCard> cards = playingBoard.getCardsPossibleToMoveToFoundation();
                for(ColumnAndCard cc : cards){
                    GameBoard newBoard = new GameBoard(boardId++, playingBoard.getBoardStateFreecellAndFoundation(), "move to foundation", playingBoard, i+1);
                    B:
                    for(Foundation fd : newBoard.getAllFoundations()){
                        if(fd.cardType().equals(cc.getCard().type())){
                            fd.addCard(cc.getCard(), newBoard.getColumn(cc.getColumn().getColumn()));
                            break B;
                        }
                    }
                    newBoard.printBoardState();
                    if(!memory.contains(newBoard.getBoardState())){
                        toPlaysNext.add(newBoard);
                    }
                }
                // \. foundation
                // freecell
                cards = playingBoard.getCardsPossibleToMoveToFreecell();
                for(ColumnAndCard cc : cards){
                    GameBoard newBoard = new GameBoard(boardId++, playingBoard.getBoardStateFreecellAndFoundation(), "move to freecell", playingBoard, i+1);
                    newBoard.addCardToFreeCell(cc.getCard(), cc.getColumn().getColumn());
                    newBoard.printBoardState();
                    if(!memory.contains(newBoard.getBoardState())){
                        toPlaysNext.add(newBoard);
                    }
                }
                // \. freecell
                // move card from column to column
                cards = playingBoard.getCardsPossibleToMoveFromColumnToColumn();
                for(ColumnAndCard cc : cards){
                    GameBoard newBoard = new GameBoard(boardId++, playingBoard.getBoardStateFreecellAndFoundation(), "move card from col to col", playingBoard, i+1);
                    newBoard.moveCard(cc);
                    newBoard.printBoardState();
                    if(!memory.contains(newBoard.getBoardState())){
                        toPlaysNext.add(newBoard);
                    }
                }
                // \. move card from column to column
                // move cards from column to column
                
                // \. move cards from column to column
            }// \. for(GameBoard playingBoard : toPlays){
            
            toPlays.clear();
            toPlays.addAll(toPlaysNext);
        }
        
        
    }
    
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
sb.append(" _2C                              \n");
sb.append(" _QC _AD _5S _9S _KH _KS _6D _2S  \n");
sb.append(" _3H _4S _5H _4D _2D _3D _TD _7H  \n");
sb.append(" _AH _6C _4H _TH _7D _AC _9D _9C  \n");
sb.append(" _5D _7C _5C _6S _3S _QH _4C _JS  \n");
sb.append(" _8C _TS _7S _8D _TC _8S _8H _KC  \n");
sb.append(" _6H _JD _2H _KD _JC _AS _QD _3C  \n");
sb.append(" _9H     _QS                      \n");
sb.append("         _JH                      \n");
        GameBoard gb = new GameBoard(0, sb.toString(), "0", null, 0);
        
    }
}
