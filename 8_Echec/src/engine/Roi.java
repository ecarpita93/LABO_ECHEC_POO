package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Roi extends Piece  {

    private Point[] kingMatrix = {new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0), new Point(1, -1), new Point(-1, 1), new Point(1, 1), new Point(-1, -1)};


    public Roi(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
        if(couleur == PlayerColor.WHITE){
            chessboard.setWhite_king(this);
        } else {
            chessboard.setBlack_king(this);
        }
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkMovesAndEatsMatrix(kingMatrix);
    }

}
