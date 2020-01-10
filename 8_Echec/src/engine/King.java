package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class King extends FirstMovePiece  {

    private Point[] kingMatrix = {new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0), new Point(1, -1), new Point(-1, 1), new Point(1, 1), new Point(-1, -1)};


    public King(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position, int pieceID) {
        super(chessboard, player, piece_type, position, pieceID);
        chessboard.setPlayerKing(player,this);
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkMovesAndEatsMatrix(kingMatrix);
    }


//    public boolean isLittleCastling(){
//        if (chessboard)
//        return false;
//    }
//
//    public boolean isBigCastling(){
//        return false;
//    }
}
