package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Cavalier extends Piece {
    public Cavalier(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {

        if (Math.abs(position.getX() - toX) == 2 && Math.abs(position.getY() - toY) == 1) {
            return true;
        }

        if (Math.abs(position.getX() - toX) == 1 && Math.abs(position.getY() - toY) == 2) {
            return true;
        }

        return false;

    }
}
