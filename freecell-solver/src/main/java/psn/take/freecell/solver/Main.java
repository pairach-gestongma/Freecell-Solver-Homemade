/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psn.take.freecell.solver;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * C black round tree
 * D red square
 * S black angle tree
 * H red heart
 * @author pairach.g
 */
public class Main {
    
    private static final Logger logger 
      = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
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
        logger.debug("board.isBoardValid():" + board.isBoardValid());
        board.printBoardState();
        //System.out.println(board.getBoardStateFreecellAndFoundation());
        
        List<String> memory = new ArrayList();
        List<GameBoard> toPlays = new ArrayList();
        toPlays.add(board);
        memory.add(board.getBoardState());
        int maxDepth = 200;
        A:
        for(int i=0;i<maxDepth;i++){
            
            List<GameBoard> toPlaysNext = new ArrayList();
            A1:
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
                    if(!memory.contains(newBoard.getBoardState())
                            && newBoard.maxSubMinInFoundation() <= 2){
                        memory.add(newBoard.getBoardState());
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
                        memory.add(newBoard.getBoardState());
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
                        memory.add(newBoard.getBoardState());
                        toPlaysNext.add(newBoard);
                    }
                }
                // \. move card from column to column
                // move cards from column to column
                cards = playingBoard.getPairCardPossibleToMoveToColumn();
                for(ColumnAndCard cc : cards){
                    GameBoard newBoard = new GameBoard(boardId++, playingBoard.getBoardStateFreecellAndFoundation(), "move cards from col to col", playingBoard, i+1);
                    newBoard.moveCards(cc);
                    newBoard.printBoardState();
                    if(!memory.contains(newBoard.getBoardState())){
                        memory.add(newBoard.getBoardState());
                        toPlaysNext.add(newBoard);
                    }
                }
                // \. move cards from column to column
                // move card from freecell to foundation
                List<Card> cards2 = playingBoard.getFreecellCardPossibleToMoveToFoundation();
                for(Card cc : cards2){
                    GameBoard newBoard = new GameBoard(boardId++, playingBoard.getBoardStateFreecellAndFoundation(), "move card from freecell to foundation", playingBoard, i+1);
                    List<Foundation> fdtns = newBoard.getAllFoundations();
                    B:
                    for(Foundation fdtn : fdtns){
                        if(!fdtn.cardType().equals(cc.type())){
                            continue B;
                        }
                        fdtn.addCardFromFreecell(cc, newBoard.getAllFreeCells());
                        break B;
                    }
                    newBoard.printBoardState();
                    if(!memory.contains(newBoard.getBoardState())){
                        memory.add(newBoard.getBoardState());
                        toPlaysNext.add(newBoard);
                    }
                }
                // \. move card from freecell to foundation
                // move card from freecell to column
                cards = playingBoard.getFreecellCardPossibleToMoveToColumn();
                for(ColumnAndCard cc : cards){
                    GameBoard newBoard = new GameBoard(boardId++, playingBoard.getBoardStateFreecellAndFoundation(), "move card from freecell to column", playingBoard, i+1);
                    newBoard.moveCardFromFreecell(cc);
                    newBoard.printBoardState();
                    if(!memory.contains(newBoard.getBoardState())){
                        memory.add(newBoard.getBoardState());
                        toPlaysNext.add(newBoard);
                    }
                }
                // \. move card from freecell to column
                
            }// \. for(GameBoard playingBoard : toPlays){
            
            // choose some board to play next
            int point = -1000000;
            List<GameBoard> winner = new ArrayList();
            for(GameBoard tp : toPlaysNext){
                logger.debug("checkBoardPoint:"+tp.getBoardId()+"=" + tp.boardPoint());
                if(tp.boardPoint() > point){
                    point = tp.boardPoint();
                    winner.clear();
                    winner.add(tp);
                    //logger.debug("this is candidate");
                }else if(tp.boardPoint() == point){
                    winner.add(tp);
                }
            }
            StringBuilder sb1 = new StringBuilder();
            sb1.append("winner for depth:" + (i+1)).append(" amnt:").append(winner.size()).append("\n");
            for(GameBoard wn : winner){
                sb1.append("boardId:" + wn.getBoardId() + "\n");
            }
            logger.debug(sb1.toString());
            // \. choose some board to play next
            
            toPlays.clear();
            toPlays.addAll(winner);
        }// for(int i=0;i<maxDepth;i++)
        
        
    }
    
    public static void mainTestGetPairs(String[] args) {
        StringBuilder sb = new StringBuilder();
sb.append("_2C                              \n");
sb.append("_QC _AD _5S _9S _KH _KS _6D _2S  \n");
sb.append("_3H _4S _5H _4D _2D _3D _TD _7H  \n");
sb.append("_AH _6C _4H _TH _7D _AC _9D _9C  \n");
sb.append("_5D _7C _5C _6S _3S _QH _4C _JS  \n");
sb.append("_8C _TS _7S _8D _TC _8S _8H _KC  \n");
sb.append("_6H _JD _2H _KD _JC _AS _QD _3C  \n");
sb.append("_9H     _QS                      \n");
sb.append("        _JH                      \n");
        GameBoard gb = new GameBoard(0, sb.toString(), "0", null, 0);
        gb.intialPrevNextAllCards();
        List<ColumnAndCard> pairColumns = gb.getPairColumns();
        System.out.println("pairColumns.size():" + pairColumns.size());
        for(ColumnAndCard cac : pairColumns){
            for(Card c : cac.getCards()){
                System.out.println("c:" + c.name());
            }
        }
    }
}
