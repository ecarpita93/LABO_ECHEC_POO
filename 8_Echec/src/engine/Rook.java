package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Rook extends Piece {
    public Rook(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position, int piece_ID) {
        super(chessboard, player, piece_type, position, piece_ID);
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        if (!chessboard.getCheck(player)) {
            checkHorizontalMovesAndEats();
            checkVerticalMovesAndEats();
        }
    }

}
