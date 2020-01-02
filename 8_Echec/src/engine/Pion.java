package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pion extends Piece {

    private boolean firstMove;

    public Pion(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
        firstMove = true;
    }


    @Override
    public boolean canMoveTo(int toX, int toY) {

        if (checkVerticalMove(toX)) {
            if (toY - position.getY() == 2) {
                if (firstMove) {
                    firstMove = false;
                    return true;
                }
            }

            if (toY - position.getY() == 1) {
                return true;
            }

        }

        return false;
    }
}
