package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Roi extends Piece  {
    public Roi(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {

        if (Math.abs(position.getX() - toX) <= 1 && Math.abs(position.getY() - toY) <= 1) {
            return true;
        }
        return false;
    }
}
