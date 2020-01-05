package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pion extends Piece {

    private boolean firstMove;
    private int vector;

    private Point[] pawnMoveMatrix = {new Point(0, 1), new Point(0, 2)};
    private Point[] pawnEatMatrix = {new Point(1, 1), new Point(-1, 1)};

    public Pion(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
        firstMove = true;
        vector = this.player == PlayerColor.BLACK ? -1 : 1;
    }


    private void checkPawnMoves() {
        for (Point other : pawnMoveMatrix) {
            Piece obstacle;
            Point tester = new Point(position);
            tester.translate((int) other.getX(), (int) other.getY() * vector);
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle == null) {
                    possible_moves.add(new Point(tester));
                }
            }

            if (firstMove) {
                firstMove = false;
            } else {
                break;
            }
        }
    }

    private void checkPawnEats() {
        for (Point other : pawnEatMatrix) {
            Piece obstacle;
            Point tester = new Point(position);
            tester.translate((int) other.getX(), (int) other.getY() * vector);
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle != null && obstacle.getPlayer() != this.player) {
                    possible_eats.add(new Point(tester));
                }
            }
        }
    }


    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkPawnMoves();
        checkPawnEats();

    }
}
