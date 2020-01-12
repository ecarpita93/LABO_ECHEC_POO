package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;

public class Pawn extends FirstMovePiece {

    private int vector;
    private boolean double_start;
    private boolean can_en_passant;

    private static final Point[] PAWN_MOVE_MATRIX = {new Point(0, 1), new Point(0, 2)};
    private static final Point[] PAWN_EAT_MATRIX = {new Point(1, 1), new Point(-1, 1)};
    private static final int WHITE_VECTOR = 1;
    private static final int BLACK_VECTOR = -1;
    private static final int WHITE_PROMOTION_Y = 7;
    private static final int BLACK_PROMOTION_Y = 0;
    private static final int DOUBLE_START_OFFSET = 2;

    public Pawn(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
        vector = this.player == PlayerColor.BLACK ? BLACK_VECTOR : WHITE_VECTOR;
        double_start = false;
        can_en_passant = false;
    }

    public boolean canBePromoted() {
        return position.getY() == WHITE_PROMOTION_Y || position.getY() == BLACK_PROMOTION_Y;
    }

    public void hasDoubleStart(int toY) {
        double_start = (Math.abs(position.getY() - toY) == DOUBLE_START_OFFSET);
    }

    public boolean isDoingEnPassant(int toX, int toY) {
        if (can_en_passant) {
            return (Math.abs(position.getX() - toX) - Math.abs(position.getY() - toY) == 0);
        }
        return false;
    }

    private boolean getDoubleStart() {
        return double_start;
    }

    private void checkPawnMoves() {
        for (Point other : PAWN_MOVE_MATRIX) {
            Piece obstacle;
            Point tester = new Point(position);
            tester.translate((int) other.getX(), (int) other.getY() * vector);
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle == null) {
                    possible_moves.add(new Point(tester));
                }
            }

            if (!this.getFirstMove()) {
                break;
            }
        }
    }

    private void checkPawnEats() {
        for (Point other : PAWN_EAT_MATRIX) {
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

    private void checkEnPassant() {
        can_en_passant = false;

        for (Point other : PAWN_EAT_MATRIX) {
            if (chessboard.checkPositionInBoardLimits(new Point((int) (position.getX() + other.getX()), (int) position.getY()))) {
                Piece obstacle = chessboard.getPieceAtPosition((int) (position.getX() + other.getX()), (int) position.getY());

                if (obstacle instanceof Pawn && obstacle.getPlayer() != this.player) {
                    if (((Pawn) obstacle).getDoubleStart()) {
                        can_en_passant = true;
                        possible_moves.add(new Point((int) (position.getX() + other.getX()), (int) (position.getY() + other.getY() * vector)));
                    }
                }
            }
        }
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkPawnMoves();
        checkPawnEats();
        checkEnPassant();
    }

}
