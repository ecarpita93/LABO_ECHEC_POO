package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Knight extends Piece {

    private Point[] knightMatrix = {new Point(2, 1), new Point(2, -1), new Point(-2, -1), new Point(-2, 1), new Point(1, 2), new Point(1, -2), new Point(-1, -2), new Point(-1, 2)};

    public Knight(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        if (!chessboard.getCheck(player)) {
            checkMovesAndEatsMatrix(knightMatrix);
        }
    }

}
