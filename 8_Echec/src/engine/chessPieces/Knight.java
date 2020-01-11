package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;

public class Knight extends Piece {

    private static final Point[] KNIGHT_MATRIX = {new Point(2, 1), new Point(2, -1), new Point(-2, -1), new Point(-2, 1), new Point(1, 2), new Point(1, -2), new Point(-1, -2), new Point(-1, 2)};

    public Knight(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
            checkMovesAndEatsMatrix(KNIGHT_MATRIX);
    }

}
