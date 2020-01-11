package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;

public class Rook extends FirstMovePiece {

    public Rook(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
            checkHorizontalMovesAndEats();
            checkVerticalMovesAndEats();
    }
}
