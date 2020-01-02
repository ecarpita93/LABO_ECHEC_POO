package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pion extends Piece {

    private boolean firstMove;
    private int vector;

    public Pion(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
        firstMove = true;
        vector = this.couleur == PlayerColor.BLACK ? -1 : 1;
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {


        if (checkVerticalMove(toX)) {
            if ((toY - position.getY()) * vector == 2 && firstMove) {
                    firstMove = false;
                    return true;
            }

            if ((toY - position.getY()) * vector == 1) {
                return true;
            }
        }

        return false;
    }
}
