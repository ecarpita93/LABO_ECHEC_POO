package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Roi extends Piece  {
    public Roi(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
        if(couleur == PlayerColor.WHITE){
            chessboard.setWhite_king(this);
        } else {
            chessboard.setBlack_king(this);
        }
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {

        if (Math.abs(position.getX() - toX) <= 1 && Math.abs(position.getY() - toY) <= 1) {
            return true;
        }
        return false;
    }
}
