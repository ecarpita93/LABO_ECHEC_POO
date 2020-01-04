package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Tour extends Piece {
    public Tour(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {

        if (checkVerticalMove(toX)) {
            return true;
        }

        if (checkHorizontalMove(toY)) {
            return true;
        }

        return false;
    }
}
