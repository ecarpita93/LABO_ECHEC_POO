package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pion extends Piece {

    private boolean firstMove;
    private int vector;

    public Pion(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
        firstMove = true;
        vector = this.player == PlayerColor.BLACK ? -1 : 1;
    }

    private boolean checkForClearPath(int x, int y) {
        return chessboard.getPieceAtPosition(x, y) == null;
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {

        if (checkVerticalMove(toX)) {
           // if (checkForClearPath(toX, toY)) {
                if ((toY - position.getY()) * vector == 2 && firstMove) {
                    firstMove = false;
                    return true;
                }

                if ((toY - position.getY()) * vector == 1) {
                    return true;
                }
            }
       // }
        return false;
    }

    @Override
    public boolean canEatTo(Piece piece_to_eat) {
        return Math.abs(position.getX() - piece_to_eat.position.getX()) <= 1 &&
                Math.abs(position.getY() - piece_to_eat.position.getY()) <= 1;
    }
}
