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
public  enum Cards {
    _AC(1,CardTypes.C), _2C(2,CardTypes.C), _3C(3,CardTypes.C), _4C(4,CardTypes.C), _5C(5,CardTypes.C), _6C(6,CardTypes.C), _7C(7,CardTypes.C), _8C(8,CardTypes.C), _9C(9,CardTypes.C), _TC(10,CardTypes.C), _JC(11,CardTypes.C), _QC(12,CardTypes.C), _KC(13,CardTypes.C),
    _AD(1,CardTypes.D), _2D(2,CardTypes.D), _3D(3,CardTypes.D), _4D(4,CardTypes.D), _5D(5,CardTypes.D), _6D(6,CardTypes.D), _7D(7,CardTypes.D), _8D(8,CardTypes.D), _9D(9,CardTypes.D), _TD(10,CardTypes.D), _JD(11,CardTypes.D), _QD(12,CardTypes.D), _KD(13,CardTypes.D),
    _AS(1,CardTypes.S), _2S(2,CardTypes.S), _3S(3,CardTypes.S), _4S(4,CardTypes.S), _5S(5,CardTypes.S), _6S(6,CardTypes.S), _7S(7,CardTypes.S), _8S(8,CardTypes.S), _9S(9,CardTypes.S), _TS(10,CardTypes.S), _JS(11,CardTypes.S), _QS(12,CardTypes.S), _KS(13,CardTypes.S),
    _AH(1,CardTypes.H), _2H(2,CardTypes.H), _3H(3,CardTypes.H), _4H(4,CardTypes.H), _5H(5,CardTypes.H), _6H(6,CardTypes.H), _7H(7,CardTypes.H), _8H(8,CardTypes.H), _9H(9,CardTypes.H), _TH(10,CardTypes.H), _JH(11,CardTypes.H), _QH(12,CardTypes.H), _KH(13,CardTypes.H);
    
    private int val;
    private CardTypes type;
    Cards(int val, CardTypes type){
        this.val = val;
        this.type = type;
    }
    
    public int val(){
        return val;
    }
    
    public CardTypes type(){
        return type;
    }
    
    public boolean isSameType(Cards card){
        return this.type.equals(card.type());
    }
}
