package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pawn extends Piece {

    private boolean firstMove;
    private int vector;

    private Point[] pawnMoveMatrix = {new Point(0, 1), new Point(0, 2)};
    private Point[] pawnEatMatrix = {new Point(1, 1), new Point(-1, 1)};

    public Pawn(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position, int piece_ID) {
        super(chessboard, player, piece_type, position, piece_ID);
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

            if (!firstMove) {
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
        if (!chessboard.getCheck(player)) {
            checkPawnMoves();
            checkPawnEats();
        }
    }

    public boolean getFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
